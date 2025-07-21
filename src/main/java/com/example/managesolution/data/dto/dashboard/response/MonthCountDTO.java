package com.example.managesolution.data.dto.dashboard.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthCountDTO {
    private String month;   // yyyy-MM
    private Integer count;
}
