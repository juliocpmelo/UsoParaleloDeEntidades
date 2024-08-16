package com.usoparalelo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ParallelUsageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParallelUsageApplication.class, args);
    }
}
