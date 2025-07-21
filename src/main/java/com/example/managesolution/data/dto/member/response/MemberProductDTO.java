package com.example.managesolution.data.dto.member.response;

import com.example.managesolution.data.enumerate.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MemberProductDTO {
    private Long memberId;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private String gender;
    private Status status;
    private String productName; // 현재 활성화된 상품명 (nullable)
    private LocalDateTime createdAt;
}
