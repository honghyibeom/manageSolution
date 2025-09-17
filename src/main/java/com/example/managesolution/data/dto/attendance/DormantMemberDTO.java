package com.example.managesolution.data.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DormantMemberDTO {
    private String memberName;
    private String phone;
    private String productName;
    private LocalDate lastAttendanceDate;
}
