package com.example.managesolution.data.dto;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.enumerate.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDTO {
    // 회원 정보
    private Long memberId;
    private String name;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String gender;
    private Status status;
    private String memo;

    // 선택된 상품 타입
    private String productType;

    // 회원권 정보
    private Long membershipProductId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate membershipStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate membershipEndDate;
    private Integer membershipPrice;

    // PT 패키지 정보
    private Long ptProductId;
    private Long trainerId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ptStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ptEndDate;
    private Integer ptTotalCount;
    private Integer ptPrice;
}
