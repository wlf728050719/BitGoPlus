package cn.bit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperties {
    private long datacenterId;
    private String applicationName;
}
