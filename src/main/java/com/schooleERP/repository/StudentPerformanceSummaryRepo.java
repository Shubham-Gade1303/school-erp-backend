package com.schooleERP.repository;

import com.schooleERP.entity.StudentPerformanceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentPerformanceSummaryRepo extends JpaRepository<StudentPerformanceSummary , Long> {

    Optional<StudentPerformanceSummary> findByStudentIdAndExamId(Long studentId, Long examId);

    List<StudentPerformanceSummary> findByStudentId(Long studentId);

    List<StudentPerformanceSummary> findByExamId(Long examId);

    boolean existsByStudentIdAndExamId(Long studentId, Long examId);

}
