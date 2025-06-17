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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);

    }

}
