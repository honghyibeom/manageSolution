package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long sessionId;
    private Long memberId;
    private Long trainerId;
    private LocalDateTime sessionTime;
    private String status; // RESERVED, COMPLETED, CANCELED
}
