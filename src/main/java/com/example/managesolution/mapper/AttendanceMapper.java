package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Attendance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttendanceMapper {

    void insertAttendance(Attendance attendance);
}
