package cn.bit.message.util;

import cn.bit.core.constant.RedisExpire;
import cn.bit.core.constant.SecurityConstant;
import cn.bit.core.exception.BizException;
import cn.bit.core.exception.SysException;
import cn.bit.core.util.RandomUtil;
import cn.bit.message.mail.BitGoMailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class CodeUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final BitGoMailSender bitGoMailSender;

    private Long lockExpireLeft(String lock) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(lock))) {
            String lockTimeStr = (String) redisTemplate.opsForValue().get(lock);
            if (lockTimeStr == null) {
                throw new SysException("cache error");
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
        String code = RandomUtil.getRandomNumberString(SecurityConstant.CODE_LENGTH);
        long leftTime = lockExpireLeft(lock);
        if (leftTime > 0) {
            throw new BizException("验证码发送频繁，请等待" + leftTime / 1000 + "秒后再次发送");
        } else {
            bitGoMailSender.send(this, email, "BitGo", message + "验证码为" + code);
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
