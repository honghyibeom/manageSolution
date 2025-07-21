package com.example.managesolution.data.dto.dashboard.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardTimeLineDTO {
    private List<DashboardSessionDTO> todaySessions;
    private Map<String, List<DashboardSessionDTO>> todaySessionsByTrainer;
    private Integer maxSessionCount;
    private List<String> timeLabels;
}
