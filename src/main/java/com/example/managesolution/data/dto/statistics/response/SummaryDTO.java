package com.example.managesolution.data.dto.statistics.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryDTO {
    private Long totalAmount;
    private Integer count;
}
