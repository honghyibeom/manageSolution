package com.example.managesolution.data.dto.ptSession.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateGroupDTO {
    private LocalDate date; // "8/12(화)" 같은 포맷
    private int total;
    private int booked;
    private int attended;
    private int noShow;
    private double rate;
    private List<SessionViewDTO> sessions;
}
