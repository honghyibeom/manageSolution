package com.example.managesolution.data.dto;

import com.example.managesolution.data.enumerate.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class MemberDTO {
    private Long memberId;
    private String name;
    private String phone;
    private LocalDate birth;
    private Status status;
    private LocalDateTime creatAt;
}
