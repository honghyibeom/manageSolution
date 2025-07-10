package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class MembershipSalesDTO {
    private String name;
    private String productName;
    private Integer price;
    private LocalDate date;
}
