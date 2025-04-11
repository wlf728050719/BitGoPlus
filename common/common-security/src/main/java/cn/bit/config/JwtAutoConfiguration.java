package cn.bit.config;

import cn.bit.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public JwtUtil jwtUtil(JwtProperties jwtProperties) {
        return new JwtUtil(jwtProperties.getSecret(), jwtProperties.getAccessTokenExpiration(),
            jwtProperties.getRefreshTokenExpiration(), jwtProperties.getInternalTokenExpiration());
    }
}
