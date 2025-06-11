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
public class Package {
    private Long packageId;
    private Long memberId;
    private Long trainerId;
    private Integer totalCount; // 구매한 pt 횟수
    private Integer usedCount; // 사용한 pt 횟수
    private LocalDateTime purchasedAt;

}
