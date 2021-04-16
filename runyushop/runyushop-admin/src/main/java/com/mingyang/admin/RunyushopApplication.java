package com.mingyang.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mingyang.admin.mapper")
public class RunyushopApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunyushopApplication.class, args);
    }
}
