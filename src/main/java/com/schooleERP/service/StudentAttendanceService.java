package com.schooleERP.service;
import com.schooleERP.dto.StudentAttendanceRequest;
import com.schooleERP.dto.StudentAttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface StudentAttendanceService {

    StudentAttendanceResponse markAttendance(
            StudentAttendanceRequest request
    );

    List<StudentAttendanceResponse> getAllAttendance();

    StudentAttendanceResponse getAttendanceById(Long id);

    List<StudentAttendanceResponse> getAttendanceByStudent(
            Long studentId
    );

    List<StudentAttendanceResponse> getAttendanceByDate(
            LocalDate attendanceDate
    );

    StudentAttendanceResponse updateAttendance(
            Long id,
            StudentAttendanceRequest request
    );

    void deleteAttendance(Long id);
}

