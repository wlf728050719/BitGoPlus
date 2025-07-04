package cn.bit.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret; //密钥
    private Long accessTokenExpiration; //access token超时时间(单位秒)
    private Long refreshTokenExpiration; //refresh token超时时间(单位秒)
    private Long internalTokenExpiration; //internal token超时时间(单位秒)
}
