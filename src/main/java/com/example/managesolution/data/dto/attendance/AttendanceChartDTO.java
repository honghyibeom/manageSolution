package com.example.managesolution.data.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceChartDTO {
    private List<Integer> weeklyCounts;
    private List<Integer> timeCounts;
}
