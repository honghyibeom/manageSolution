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
    // 수업 추가
    void insert(PtSession session);
    // 같은 회원이 같은 시간대에 있는지 여부
    int existsMemberExact(@Param("memberId") Long memberId,
                           @Param("date") LocalDate date,
                           @Param("time") LocalTime time);
    // 수업 삭제
    void deleteById(@Param("id") Long sessionId);
    // 오늘 수업 일정 조회
    List<DashboardSessionDTO> findTodaySessions();
    // 특정 시간대에 존재하는 수업을 조회
    List<BusySlotDTO> findBusySlots(Long trainerId, LocalDate start, LocalDate end);
    // 수업 리스트 조회
    List<SessionViewDTO> ptSessionViews(@Param("cond")LectureSearchDTO lectureSearchDTO,
                                        @Param("limit") int limit,
                                        @Param("offset") int offset);
    // 상태 변경 (출석, 결석)
    void updateStatus(@Param("id") Long sessionId, String status);
    // 상태 조회
    String getStatus(Long sessionId);
    // 수업 수정
    void updateSession(@Param("sessionId") Long sessionId,
                       @Param("sessionDate") LocalDate sessionDate,
                       @Param("sessionTime") LocalTime sessionTime,
                       @Param("trainerId") Long trainerId);
    // 수업 그룹 삭제
    void deleteByBatchId(@Param("batchId") String batchId);
    // 수업 그룹 리스트 조회
    List<PtSession> findSessionIdByBatchId(@Param("batchId") String batchId);
    // 수업 그룹 view 조회
    List<SessionGroupDTO> findSessionViewByBatchId(@Param("batchId") String batchId);
}
