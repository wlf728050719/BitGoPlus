package cn.bit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "password-encoder")
@Data
public class BCryptPasswordEncoderProperties {
    private int strength;
}
