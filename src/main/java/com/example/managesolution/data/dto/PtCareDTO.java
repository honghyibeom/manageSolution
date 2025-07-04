package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PtCareDTO {
    private String name;
    private String phone;
    private int remainingCount;
    private int totalCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
