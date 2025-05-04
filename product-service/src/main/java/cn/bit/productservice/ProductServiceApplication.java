package cn.bit.productservice;

import cn.bit.feign.client.UserClient;
import cn.bit.feign.config.FeignConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@MapperScan("cn.bit.productservice.mapper")
@EnableFeignClients(defaultConfiguration = FeignConfiguration.class, clients = {UserClient.class})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
