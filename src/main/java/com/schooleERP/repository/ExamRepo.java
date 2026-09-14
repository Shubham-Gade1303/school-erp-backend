 package com.schooleERP.repository;

import com.schooleERP.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepo extends JpaRepository<Exam, Long> {

    List<Exam> findByAcademicYearId(Long academicYearId);

    List<Exam> findByStandardId(Long standardId);

    List<Exam> findByAcademicYearIdAndStandardId(
            Long academicYearId,
            Long standardId
    );

    boolean existsByExamNameAndAcademicYearIdAndStandardId(
            String examName,
            Long academicYearId,
            Long standardId
    );
}

