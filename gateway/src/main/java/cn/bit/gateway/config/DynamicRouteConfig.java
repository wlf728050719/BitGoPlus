package cn.bit.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "dynamic-route")
@Configuration
@Data
public class DynamicRouteConfig {
    private String dataId;
    private String group;
    private Long timeOutMs;
}
