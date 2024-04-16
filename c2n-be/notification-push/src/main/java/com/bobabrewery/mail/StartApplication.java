package com.bobabrewery.mail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);
    }

    @Bean
    public CommandLineRunner runner(MailService service){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                service.send("devcxlcn@gmail.com");
            }
        };
    }

}
