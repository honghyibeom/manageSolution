package com.example.managesolution.data.dto.dashboard.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSessionDTO {
    private String trainerName;
    private String memberName;
    private LocalTime sessionTime;
    private Double left; // getter, setter 추가
    private Double width;
    private Integer overlapIndex;
}
