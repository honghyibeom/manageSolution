package com.example.managesolution.data.dto.ptSession.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionGroupDTO {
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String memberName;
    private String trainerName;
    private String productName;
}
