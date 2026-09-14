package com.schooleERP.repository;

import com.schooleERP.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StudentAttendanceRepo extends JpaRepository<StudentAttendance, Long> {

    List<StudentAttendance> findByStudentId(Long studentId);
    List<StudentAttendance> findByAttendanceDate(LocalDate attendanceDate);
    List<StudentAttendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);


    boolean existsByStudentIdAndAttendanceDate(Long id, LocalDate attendanceDate );

}
