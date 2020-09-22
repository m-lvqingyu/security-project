package com.dream.start.browser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create By 2020/9/12
 *
 * @author lvqingyu
 */
@SpringBootApplication
@MapperScan(basePackages = "com.dream.start.browser.system.mapper")
public class CarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApiApplication.class, args);
    }
}
