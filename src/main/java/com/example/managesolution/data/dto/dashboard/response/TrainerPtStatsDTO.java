package com.example.managesolution.data.dto.dashboard.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerPtStatsDTO {
    private List<String> labels; // ["2024-02", "2024-03", ...]
    private List<TrainerDatasetDTO> datasets;

}
