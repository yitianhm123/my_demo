package com.example.sem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemApplication.class, args);
    }

}
