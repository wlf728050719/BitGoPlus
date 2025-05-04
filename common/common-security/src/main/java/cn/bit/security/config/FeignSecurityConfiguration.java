package cn.bit.security.config;

import cn.bit.core.constant.SecurityConstant;
import cn.bit.security.util.JwtUtil;
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
            template.header(SecurityConstant.HEADER_AUTHORIZATION, SecurityConstant.TAG_INTERNAL + token);
            template.header(SecurityConstant.HEADER_SOURCE, SecurityConstant.TAG_SERVICE + serviceName);
        };
    }
}
