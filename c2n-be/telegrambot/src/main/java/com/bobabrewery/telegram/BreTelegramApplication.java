package com.bobabrewery.telegram;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.brewery.bretelegram.mapper")
public class BreTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreTelegramApplication.class, args);
    }

}
