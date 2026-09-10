package com.schooleERP.repository;

import com.schooleERP.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByAdmissionNumber(String admissionNumber);

    boolean existsByAdmissionNumber(String admissionNumber);

    boolean existsByAdmissionNumberAndIdNot(String admissionNumber, Long id);

    List<Student> findByClassSectionId(Long classSectionId);

    List<Student> findByActiveTrue();
}

