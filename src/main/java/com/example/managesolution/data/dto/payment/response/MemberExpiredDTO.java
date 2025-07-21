package com.example.managesolution.data.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberExpiredDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredDate;
    private Long memberId;
    private String memberName;
    private String phone;
    private String previousProduct;
    private String registeredProduct;
    private String productType;
    private Integer amount;
}
