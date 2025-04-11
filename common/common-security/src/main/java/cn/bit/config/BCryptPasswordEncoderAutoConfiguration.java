package cn.bit.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableConfigurationProperties(BCryptPasswordEncoderProperties.class)
public class BCryptPasswordEncoderAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BCryptPasswordEncoder bCryptPasswordEncoder(BCryptPasswordEncoderProperties properties) {
        return new BCryptPasswordEncoder(properties.getStrength());
    }
}
