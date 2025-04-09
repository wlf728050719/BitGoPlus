package cn.bit.snowflake.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import cn.bit.snowflake.core.DistributedSnowflakeIdGenerator;

@Configuration
@ConditionalOnClass(RedisTemplate.class)
@EnableConfigurationProperties(SnowflakeProperties.class)
public class SnowflakeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DistributedSnowflakeIdGenerator distributedSnowflakeIdGenerator(SnowflakeProperties properties,
        RedisTemplate<String, Object> redisTemplate) throws Exception {
        return new DistributedSnowflakeIdGenerator(properties.getApplicationName(), redisTemplate,
            properties.getDatacenterId());
    }

}
