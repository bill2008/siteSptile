package com.accenture.sptile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.accenture.sptile.mapper")
public class SptileApplication {

    public static void main(String[] args) {
        SpringApplication.run(SptileApplication.class, args);
    }

}
