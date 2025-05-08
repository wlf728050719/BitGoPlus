package cn.bit.userservice;

import cn.bit.feign.client.UserClient;
import cn.bit.feign.config.FeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.bit.userservice.mapper")
@EnableCaching
@EnableAsync
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class, clients = {UserClient.class})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
