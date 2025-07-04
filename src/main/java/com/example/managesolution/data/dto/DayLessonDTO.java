package com.example.managesolution.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayLessonDTO {
    private int day; // 1~31
    private int lessonCount; // 수업 개수
    private boolean enabled; // 이번 달 날짜인지 여부 (빈 셀 여부 판단용)
}
