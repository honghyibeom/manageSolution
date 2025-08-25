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
public class BusySlotDTO {
    private LocalDate sessionDate;
    private LocalTime sessionTime;
}
