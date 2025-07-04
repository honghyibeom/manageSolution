package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberShipCareDTO {
    private String name;
    private String phone;
    private String productName;
    private LocalDate startDate;
    private LocalDate endDate;
}
