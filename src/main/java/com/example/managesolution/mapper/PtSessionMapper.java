package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.dashboard.response.DashboardSessionDTO;
import com.example.managesolution.data.dto.ptSession.response.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface PtSessionMapper {

    void insert(PtSession session);

    int existsMemberExact(@Param("memberId") Long memberId,
                           @Param("date") LocalDate date,
                           @Param("time") LocalTime time);

    void deleteById(@Param("id") Long sessionId);

    List<DashboardSessionDTO> findTodaySessions();

    List<BusySlotDTO> findBusySlots(Long trainerId, LocalDate start, LocalDate end);

    List<SessionViewDTO> ptSessionViews(@Param("cond")LectureSearchDTO lectureSearchDTO,
                                        @Param("limit") int limit,
                                        @Param("offset") int offset);

    void updateStatus(@Param("id") Long sessionId, String status);

    String getStatus(Long sessionId);

    void updateSession(@Param("sessionId") Long sessionId,
                       @Param("sessionDate") LocalDate sessionDate,
                       @Param("sessionTime") LocalTime sessionTime,
                       @Param("trainerId") Long trainerId);

    void deleteByBatchId(@Param("batchId") String batchId);

    List<PtSession> findSessionIdByBatchId(@Param("batchId") String batchId);

    List<SessionGroupDTO> findSessionViewByBatchId(@Param("batchId") String batchId);
}
