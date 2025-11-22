package com.example.trainerintake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.trainerintake")
public class TrainerIntakeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainerIntakeApplication.class, args);
    }
}
