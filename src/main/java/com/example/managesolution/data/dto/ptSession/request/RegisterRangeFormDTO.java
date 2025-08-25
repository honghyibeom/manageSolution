package com.example.managesolution.data.dto.ptSession.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRangeFormDTO {
    @NotNull private Long trainerId;
    @NotNull private Long memberId;
    @NotNull private Long packageId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    // 월=1 ... 일=7 (체크박스 name="repeatDays")
    private List<@Min(1) @Max(7) Integer> repeatDays;

}
