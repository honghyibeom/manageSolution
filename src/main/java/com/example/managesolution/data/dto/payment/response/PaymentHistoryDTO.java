package com.example.managesolution.data.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentHistoryDTO {
    private LocalDateTime paymentDate;
    private String memberName;
    private String phone;
    private String productName;
    private String productType;
    private Integer amount;
    private String method;
    private LocalDate startDate;
    private LocalDate endDate;

}
