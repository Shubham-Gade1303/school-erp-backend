
package com.schooleERP.repository;

import com.schooleERP.entity.StudentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAddressRepo extends JpaRepository<StudentAddress, Long> {

    Optional<StudentAddress> findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);
}

