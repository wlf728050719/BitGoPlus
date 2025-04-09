package cn.bit.snowflake.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperties {
    private long datacenterId;
    private String applicationName;
}
