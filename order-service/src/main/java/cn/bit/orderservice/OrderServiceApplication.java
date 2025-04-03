package cn.bit.orderservice;

import cn.bit.feign.client.UserClient;
import cn.bit.feign.config.FeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.bit.orderservice.mapper")
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class,clients = {UserClient.class})
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
