package com.example.managesolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ManageSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageSolutionApplication.class, args);
    }

}
