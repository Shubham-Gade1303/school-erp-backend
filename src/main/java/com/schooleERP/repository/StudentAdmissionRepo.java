package com.schooleERP.repository;

import com.schooleERP.entity.StudentAdmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAdmissionRepo extends JpaRepository<StudentAdmission, Long> {

    Optional<StudentAdmission> findByStudentId(Long studentId);

    List<StudentAdmission> findByAcademicYearId(Long academicYearId);

    List<StudentAdmission> findByStandardId(Long standardId);

    List<StudentAdmission> findByClassSectionId(Long classSectionId);

    List<StudentAdmission> findByAdmissionStatus(String admissionStatus);

    boolean existsByStudentId(Long studentId);
}

