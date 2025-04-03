package cn.bit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PreDestroy;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DistributedSnowflakeIdGenerator {
    // ==================== 常量定义 ====================
    private static final long DATACENTER_ID_BITS = 5L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private static final long DEFAULT_EPOCH = 1577836800000L; // 2020-01-01 00:00:00
    private static final int DEFAULT_HEARTBEAT_INTERVAL = 30; // 心跳间隔(秒)
    private static final int DEFAULT_LOCK_TIMEOUT = 60; // 锁超时时间(秒)
    private static final int DEFAULT_HEARTBEAT_EXPIRE = 90; // 心跳过期时间(秒)
    private static final int MAX_TOLERATE_BACKWARD_MS = 5; // 最大容忍时钟回拨(毫秒)
    private static final int DEFAULT_BUFFER_SIZE = 20000; // 缓冲区大小
    private static final int DEFAULT_BUFFER_THRESHOLD = 10000; // 缓冲区阈值

    // ==================== 实例字段 ====================
    private final long epoch; // 起始时间戳
    private final long datacenterId; // 数据中心ID
    private final long workerId; // 工作节点ID
    private final AtomicLong lastTimestamp = new AtomicLong(-1L); // 上次生成ID的时间戳
    private final AtomicLong sequence = new AtomicLong(0L); // 序列号
    private final AtomicLong generatedIds = new AtomicLong(0); // 生成的ID总数统计
    private final AtomicLong clockBackwardsEvents = new AtomicLong(0); // 时钟回拨事件计数

    private final RedisTemplate<String, Object> redisTemplate; // Redis操作模板
    private final String workerKeyPrefix; // Redis中worker键的前缀
    private final ScheduledExecutorService heartbeatExecutor; // 心跳执行器
    private final String nodeIdentifier; // 节点唯一标识符
    private final int heartbeatInterval; // 心跳间隔时间(秒)
    private final int lockTimeout; // 锁超时时间(秒)
    private final int heartbeatExpire; // 心跳过期时间(秒)

    private final BlockingQueue<Long> idBuffer; // ID缓冲区
    private final ExecutorService bufferExecutor; // 缓冲区填充执行器
    private final int bufferSize; // 缓冲区大小
    private final int bufferThreshold; // 缓冲区填充阈值

    // 并发控制锁
    private final Lock idGenerationLock = new ReentrantLock();

    public DistributedSnowflakeIdGenerator(String serviceName, RedisTemplate<String, Object> redisTemplate,
                                           long datacenterId) throws Exception {
        this(serviceName, redisTemplate, datacenterId, DEFAULT_EPOCH,
                DEFAULT_HEARTBEAT_INTERVAL, DEFAULT_LOCK_TIMEOUT, DEFAULT_HEARTBEAT_EXPIRE,
                DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_THRESHOLD);
    }

    public DistributedSnowflakeIdGenerator(String serviceName, RedisTemplate<String, Object> redisTemplate,
                                           long datacenterId, long epoch, int heartbeatInterval,
                                           int lockTimeout, int heartbeatExpire, int bufferSize,
                                           int bufferThreshold) throws Exception {
        // 参数校验
        validateParameters(datacenterId, serviceName);

        this.epoch = epoch;
        this.datacenterId = datacenterId;
        this.redisTemplate = redisTemplate;
        this.heartbeatInterval = heartbeatInterval;
        this.lockTimeout = lockTimeout;
        this.heartbeatExpire = heartbeatExpire;
        this.bufferSize = bufferSize;
        this.bufferThreshold = bufferThreshold;
        this.idBuffer = new LinkedBlockingQueue<>(bufferSize);
        this.bufferExecutor = Executors.newSingleThreadExecutor();

        // 生成节点唯一标识符(IP+进程ID)
        this.nodeIdentifier = generateNodeIdentifier();
        log.info("生成节点唯一标识符: {}", nodeIdentifier);

        // 构造Redis中worker键的前缀
        this.workerKeyPrefix = String.format("snowflake:%s:dc-%d:worker-", serviceName, datacenterId);
        log.info("初始化Redis键前缀: {}", workerKeyPrefix);

        // 注册并获取worker ID
        this.workerId = registerAndGetWorkerId();
        log.info("成功分配worker ID: {}, 数据中心ID: {}", workerId, datacenterId);

        // 启动心跳维护和缓冲区填充
        this.heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        startHeartbeat();
        startBufferRefill();

        log.info("雪花ID生成器初始化完成, 时间戳起点: {}, 缓冲区大小: {}, 填充阈值: {}",
                epoch, bufferSize, bufferThreshold);
    }

    private void validateParameters(long datacenterId, String serviceName) {
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException(
                    String.format("数据中心ID不能大于 %d 或小于 0", MAX_DATACENTER_ID));
        }
        if (serviceName == null || serviceName.isEmpty()) {
            throw new IllegalArgumentException("服务名称不能为空");
        }
    }

    private String generateNodeIdentifier() throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String processId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        return hostAddress + "-" + processId;
    }

    private long registerAndGetWorkerId() {
        String lockKey = getWorkerLockKey();
        log.info("尝试获取worker ID分配锁, 锁键: {}", lockKey);

        Boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, nodeIdentifier,
                lockTimeout, TimeUnit.SECONDS);

        if (lockAcquired != null && lockAcquired) {
            log.info("成功获取worker ID分配锁");
            try {
                long allocatedWorkerId = allocateWorkerId();
                log.info("分配worker ID成功: {}", allocatedWorkerId);
                return allocatedWorkerId;
            } finally {
                redisTemplate.delete(lockKey);
                log.info("释放worker ID分配锁");
            }
        }
        log.error("获取worker ID分配锁失败");
        throw new IllegalStateException("无法获取worker ID分配锁");
    }

    private String getWorkerLockKey() {
        return workerKeyPrefix + "lock";
    }

    private String getWorkerKey(long workerId) {
        return workerKeyPrefix + workerId;
    }

    private long allocateWorkerId() {
        log.info("开始分配worker ID...");
        Set<Long> usedWorkerIds = new HashSet<>();

        // 扫描已使用的worker ID
        for (long id = 0; id <= MAX_WORKER_ID; id++) {
            if (Boolean.TRUE.equals(redisTemplate.hasKey(getWorkerKey(id)))) {
                usedWorkerIds.add(id);
            }
        }
        log.info("已使用的worker ID: {}", usedWorkerIds);

        // 查找可用的worker ID
        for (long id = 0; id <= MAX_WORKER_ID; id++) {
            if (!usedWorkerIds.contains(id)) {
                redisTemplate.opsForValue().set(getWorkerKey(id), nodeIdentifier,
                        heartbeatExpire, TimeUnit.SECONDS);
                log.info("成功注册worker ID: {}, 节点标识: {}, 过期时间: {}秒",
                        id, nodeIdentifier, heartbeatExpire);
                return id;
            }
        }

        log.error("数据中心 {} 中没有可用的worker ID (最大: {})", datacenterId, MAX_WORKER_ID);
        throw new IllegalStateException(
                String.format("数据中心 %d 中没有可用的worker ID (最大: %d)", datacenterId, MAX_WORKER_ID));
    }

    private void startHeartbeat() {
        String workerKey = getWorkerKey(workerId);
        log.info("启动心跳维护, worker键: {}, 间隔: {}秒", workerKey, heartbeatInterval);

        heartbeatExecutor.scheduleAtFixedRate(() -> {
            try {
                redisTemplate.expire(workerKey, heartbeatExpire, TimeUnit.SECONDS);
                log.debug("更新worker {} 的心跳", workerId);
            } catch (Exception e) {
                log.error("更新心跳失败: {}", e.getMessage());
            }
        }, 0, heartbeatInterval, TimeUnit.SECONDS);
    }

    private void startBufferRefill() {
        log.info("启动ID缓冲区填充线程, 缓冲区大小: {}, 填充阈值: {}", bufferSize, bufferThreshold);

        bufferExecutor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (idBuffer.size() <= bufferThreshold) {
                        refillBuffer();
                    }
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("填充ID缓冲区错误: {}", e.getMessage());
                }
            }
        });
    }

    private void refillBuffer() {
        int refillCount = bufferSize - idBuffer.size();
        log.info("开始填充ID缓冲区, 需要填充数量: {}", refillCount);

        for (int i = 0; i < refillCount; i++) {
            try {
                long id = generateIdInternal();
                idBuffer.put(id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("为缓冲区生成ID失败: {}", e.getMessage());
                break;
            }
        }

        log.info("ID缓冲区填充完成, 当前缓冲区大小: {}", idBuffer.size());
    }

    public long nextId() {
        try {
            Long bufferedId = idBuffer.poll(100, TimeUnit.MILLISECONDS);
            if (bufferedId != null) {
                return bufferedId;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return generateIdInternal();
    }

    private long generateIdInternal() {
        idGenerationLock.lock();
        try {
            long currentTimestamp = timeGen();
            long lastTimestampValue = lastTimestamp.get();

            // 处理时钟回拨
            if (currentTimestamp < lastTimestampValue) {
                currentTimestamp = handleClockBackwards(currentTimestamp, lastTimestampValue);
            }

            // 处理同一毫秒内请求
            if (currentTimestamp == lastTimestampValue) {
                long sequenceValue = sequence.incrementAndGet();
                if (sequenceValue > MAX_SEQUENCE) {
                    currentTimestamp = waitNextMillis(lastTimestampValue);
                    sequence.set(0);
                    sequenceValue = 0;
                }
                sequence.set(sequenceValue);
            } else {
                // 新毫秒，重置序列号
                sequence.set(0);
            }

            lastTimestamp.set(currentTimestamp);
            generatedIds.incrementAndGet();

            return ((currentTimestamp - epoch) << TIMESTAMP_SHIFT)
                    | (datacenterId << DATACENTER_ID_SHIFT)
                    | (workerId << WORKER_ID_SHIFT)
                    | sequence.get();
        } finally {
            idGenerationLock.unlock();
        }
    }

    private long handleClockBackwards(long currentTimestamp, long lastTimestamp) {
        long offset = lastTimestamp - currentTimestamp;
        clockBackwardsEvents.incrementAndGet();

        log.warn("检测到时钟回拨, 偏移量: {} 毫秒", offset);

        if (offset <= MAX_TOLERATE_BACKWARD_MS) {
            try {
                log.info("时钟回拨在容忍范围内, 等待 {} 毫秒", offset);
                TimeUnit.MILLISECONDS.sleep(offset);
                currentTimestamp = timeGen();
                if (currentTimestamp < lastTimestamp) {
                    throw new IllegalStateException("等待后时钟仍然回拨");
                }
                return currentTimestamp;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("处理时钟回拨时线程被中断");
            }
        }
        throw new IllegalStateException(
                String.format("时钟回拨过大: %d 毫秒, 拒绝生成ID", offset));
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            Thread.yield();
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    @PreDestroy
    public void preDestroy() {
        cleanup();
    }

    private void cleanup() {
        log.info("开始关闭雪花ID生成器...");
        try {
            // 关闭缓冲区执行器
            bufferExecutor.shutdown();
            if (!bufferExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                bufferExecutor.shutdownNow();
            }

            // 关闭心跳执行器
            heartbeatExecutor.shutdown();
            if (!heartbeatExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                heartbeatExecutor.shutdownNow();
            }
            log.info("雪花ID生成器关闭完成. 共生成ID数量: {}", generatedIds.get());
        } catch (Exception e) {
            log.error("清理过程中发生错误: {}", e.getMessage());
        }
    }

    // ==================== 监控指标方法 ====================
    public long getGeneratedIdsCount() {
        return generatedIds.get();
    }

    public long getClockBackwardsEventsCount() {
        return clockBackwardsEvents.get();
    }

    public int getBufferSize() {
        return idBuffer.size();
    }
}
