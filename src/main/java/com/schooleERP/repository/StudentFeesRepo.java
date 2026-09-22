package com.schooleERP.repository;

import com.schooleERP.entity.StudentFees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentFeesRepo extends JpaRepository<StudentFees, Long> {

    List<StudentFees> findByStudentId(Long studentId);

    List<StudentFees> findByAcademicYearId(Long academicYearId);

    List<StudentFees> findByStudentIdAndAcademicYearId(
            Long studentId,
            Long academicYearId
    );

    List<StudentFees> findByStatus(String status);

    List<StudentFees> findByFeeType(String feeType);

    boolean existsByStudentIdAndAcademicYearIdAndFeeType(
            Long studentId,
            Long academicYearId,
            String feeType
    );
}

