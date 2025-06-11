package com.example.managesolution.data.domain;

import com.example.managesolution.data.enumerate.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long memberId;
    private String name;
    private String phone;
    private LocalDate birth;
    private Status status;
    private LocalDateTime creatAt;
}
