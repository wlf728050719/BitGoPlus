package cn.bit.config;

import cn.bit.util.JwtUtil;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignSecurityConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor requestInterceptor(JwtUtil jwtUtil) {
        return template -> {
            // 从配置获取服务名，而不是硬编码
            String serviceName = template.feignTarget().name();
            String token = jwtUtil.generateInternalToken(serviceName);
            template.header("Authorization", "Internal " + token);
            template.header("Source", "Service " + serviceName);
        };
    }
}
