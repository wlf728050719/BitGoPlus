package cn.bit.util;

import cn.bit.constant.RedisExpire;
import cn.bit.exception.BizException;
import cn.bit.exception.SysException;
import cn.bit.mail.BitGoMailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class CodeUtil {
    private static final int CODE_LENGTH = 6;
    private final BitGoMailSender sender;
    private final RedisTemplate<String, Object> redisTemplate;

    private Long lockExpireLeft(String lock) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(lock))) {
            String lockTimeStr = (String) redisTemplate.opsForValue().get(lock);
            if (lockTimeStr == null) {
                throw new SysException("Cache Error");
            }
            long lockTime = Long.parseLong(lockTimeStr);
            long currentTime = System.currentTimeMillis();
            long remainingTime = RedisExpire.CODE_LOCK_EXPIRE_SECONDS * 1000 - (currentTime - lockTime);
            if (remainingTime > 0) {
                return remainingTime;
            } else {
                // 删除过期锁
                redisTemplate.delete(lock);
            }
        }
        return 0L;
    }

    public void sendMailCode(String lock, String key, String email, Long keyExpireSeconds, String message) {
        String code = RandomUtil.getRandomNumberString(CODE_LENGTH);
        long leftTime = lockExpireLeft(lock);
        if (leftTime > 0) {
            throw new BizException("验证码发送频繁，请等待" + leftTime / 1000 + "秒后再次发送");
        } else {
            sender.send(email, "BitGo", message + "验证码为: " + code + " 。");
            redisTemplate.opsForValue().set(key, code, keyExpireSeconds, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(lock, String.valueOf(System.currentTimeMillis()),
                RedisExpire.CODE_LOCK_EXPIRE_SECONDS, TimeUnit.SECONDS);
        }
    }

    public void checkMailCode(String key, String code) {
        String temp = (String) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(temp) || !temp.equals(code)) {
            redisTemplate.delete(key);
            throw new BizException("验证码不存在或错误,请重新发送");
        }
        redisTemplate.delete(key);
    }

}
