package com.example.managesolution.data.dto.ptSession.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionUpdateDTO {
    private Long sessionId;   // 수정할 세션 ID
    private LocalDate date;   // 선택한 날짜
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;   // 선택한 시간 (flatpickr로 정시만 들어옴)
    private Long trainerId;   // 선택한 트레이너 ID
}
