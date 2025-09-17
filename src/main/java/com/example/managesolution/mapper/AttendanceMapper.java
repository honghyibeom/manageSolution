package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Attendance;
import com.example.managesolution.data.dto.attendance.AttendanceReportDTO;
import com.example.managesolution.data.dto.attendance.DormantMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {
    // 출석 등록
    void insertAttendance(Attendance attendance);
    // 출석 리스트 조회
    List<AttendanceReportDTO> findAttendanceReport(String startDate, String endDate, String keyword);
    // 요일별 차트
    List<Map<String, Object>> findWeeklyAttendance();
    // 시간대별 차트
    List<Map<String, Object>> findTimeAttendance();
    // 휴먼 회원 조회
    List<DormantMemberDTO> findDormantMembers();

}
