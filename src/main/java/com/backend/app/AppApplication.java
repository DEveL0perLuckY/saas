package com.backend.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AppApplication.class, args);
        System.out.println("server started");
    }

}
