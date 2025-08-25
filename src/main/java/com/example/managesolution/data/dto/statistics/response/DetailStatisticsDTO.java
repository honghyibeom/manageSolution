package com.example.managesolution.data.dto.statistics.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailStatisticsDTO {
    private String memberName;
    private String productName;
    private String date;
    private int amount;
}
