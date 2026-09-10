package com.schooleERP.repository;

import com.schooleERP.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUserId(Long userId);

    Optional<Teacher> findByEmployeeCode(String employeeCode);

    boolean existsByUserId(Long userId);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCodeAndIdNot(
            String employeeCode,
            Long id
    );
}

