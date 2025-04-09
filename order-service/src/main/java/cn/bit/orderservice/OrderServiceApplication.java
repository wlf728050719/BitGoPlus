package cn.bit.orderservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import cn.bit.feign.client.UserClient;
import cn.bit.feign.config.FeignConfiguration;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class, clients = {UserClient.class})
@MapperScan("cn.bit.orderservice.mapper")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
