package com.example.managesolution.data.dto.ptSession.request;

import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private Long memberId;
    private Long sessionId;
    private Long subscriptionId;
    private String status; //"ATTENDED" or "NO_SHOW"
}
