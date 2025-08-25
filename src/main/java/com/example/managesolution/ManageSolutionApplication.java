package com.example.managesolution;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@MapperScan("com.example.managesolution.mapper")
public class ManageSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageSolutionApplication.class, args);
        System.out.println("http://localhost:8080/dashboard");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);

    }

}
