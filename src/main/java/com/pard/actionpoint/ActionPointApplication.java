package com.pard.actionpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ActionPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActionPointApplication.class, args);
    }

}
