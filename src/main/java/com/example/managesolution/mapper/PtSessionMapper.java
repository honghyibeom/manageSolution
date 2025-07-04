package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.DayLessonDTO;
import com.example.managesolution.data.dto.LessonDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface PtSessionMapper {
    List<DayLessonDTO> getSessionCountByMonth(@Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    List<LessonDTO> selectLessonsByDate(@Param("sessionDate") Date sessionDate);

    void insert(PtSession session);

    void deleteById(@Param("id") Long sessionId);
}
