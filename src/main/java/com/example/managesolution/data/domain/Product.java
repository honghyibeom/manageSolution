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
public class Product {
    private Long productId;
    private String name;
    private String type; // MEMBERSHIP / PT
    private Integer duration; // 사용 기간 (일수, PT도 기간 있음)
    private Integer count; // PT 횟수(회원권은 null)
    private Integer price;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
