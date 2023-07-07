package com.nju.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 这是一个主启动类
 * @SpringBootApplication: 这是一个SpringBoot应用
 */

@SpringBootApplication
@MapperScan("com.nju.boot.dao")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
