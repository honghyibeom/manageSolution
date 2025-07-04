package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String role; // ADMIN or Trainer
    private LocalDateTime createdAt;

}
