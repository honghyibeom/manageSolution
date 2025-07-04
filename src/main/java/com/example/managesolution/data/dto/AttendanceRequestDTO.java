package com.example.managesolution.data.dto;

import lombok.Data;

@Data
public class AttendanceRequestDTO {
    private Long memberId;
    private Long sessionId;
    private Long packageId;
    private String status; //"ATTENDED" or "NOSHOW"
}
