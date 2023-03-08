package com.example.homeexchange_simpleversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HomeExchangeSimpleVersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeExchangeSimpleVersionApplication.class, args);
    }

}
