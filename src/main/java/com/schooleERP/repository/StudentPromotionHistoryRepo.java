package com.schooleERP.repository;

import com.schooleERP.entity.StudentPromotionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPromotionHistoryRepo
        extends JpaRepository<StudentPromotionHistory, Long> {

    List<StudentPromotionHistory> findByStudentId(Long studentId);

    List<StudentPromotionHistory> findByFromAcademicYearId(Long academicYearId);

    List<StudentPromotionHistory> findByToAcademicYearId(Long academicYearId);

    List<StudentPromotionHistory> findByStatus(String status);
}
