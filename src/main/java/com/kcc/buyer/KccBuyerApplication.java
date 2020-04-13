package com.kcc.buyer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kcc.buyer.mapper")
public class KccBuyerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KccBuyerApplication.class, args);
    }
}
