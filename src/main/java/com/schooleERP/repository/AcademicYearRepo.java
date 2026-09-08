
        package com.schooleERP.repository;

import com.schooleERP.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicYearRepo
        extends JpaRepository<AcademicYear, Long> {

    Optional<AcademicYear> findByAcademicYear(String academicYear);

    Optional<AcademicYear> findByActiveTrue();

    boolean existsByAcademicYear(String academicYear);

    boolean existsByActiveTrue();
}

