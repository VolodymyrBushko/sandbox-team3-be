package com.exadel.discountwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiscountWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscountWebApplication.class, args);
    }
}
