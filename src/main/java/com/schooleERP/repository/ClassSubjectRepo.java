package com.schooleERP.repository;

import com.schooleERP.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSubjectRepo
        extends JpaRepository<ClassSubject, Long> {

    List<ClassSubject> findByAcademicYearId(Long academicYearId);

    List<ClassSubject> findByStandardId(Long standardId);

    List<ClassSubject> findBySubjectId(Long subjectId);

    List<ClassSubject> findByAcademicYearIdAndStandardId(
            Long academicYearId,
            Long standardId
    );

    boolean existsByAcademicYearIdAndStandardIdAndSubjectId(
            Long academicYearId,
            Long standardId,
            Long subjectId
    );
}

