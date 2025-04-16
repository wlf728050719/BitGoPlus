package cn.bit.redis.config;

import cn.bit.constant.RedisKey;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Override
    @Bean("DictCacheGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            if (cacheable != null) {
                return RedisKey.DICT_PREFIX + RedisKey.SEPARATOR + method.getName();
            }
            return method.getName() + Arrays.toString(params);
        };
    }
}
