package cn.bit.userservice;

import cn.bit.client.UserClient;
import cn.bit.config.FeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.bit.userservice.mapper")
@EnableCaching
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class, clients = {UserClient.class})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
