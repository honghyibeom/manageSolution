package com.example.managesolution.data.dto.care.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ImminentCareDTO {
    private String memberName;
    private String phone;
    private String productName;
    private String remainingCount;
    private LocalDate endDate;
}
