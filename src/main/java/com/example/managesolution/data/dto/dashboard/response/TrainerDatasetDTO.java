package com.example.managesolution.data.dto.dashboard.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDatasetDTO {
    private String label; // 트레이너 이름
    private List<Integer> data; // 월별 회원 수
    private String backgroundColor;
}
