package cn.bit.feign.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;

public class FeignConfiguration {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
