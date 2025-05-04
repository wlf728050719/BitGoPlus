package cn.bit.data.snowflake.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperties {
    private String serviceName;
    private Long datacenterId;
    private Long epoch;
    private Integer heartbeatIntervalSeconds;
    private Integer heartbeatExpireSeconds;
    private Integer lockTimeoutSeconds;
    private Integer maxTolerateBackwardMilliseconds;
    private Integer bufferSize;
    private Integer bufferThreshold;
}
