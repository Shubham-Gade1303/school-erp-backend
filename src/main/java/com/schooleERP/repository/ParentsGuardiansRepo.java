package com.schooleERP.repository;

import com.schooleERP.entity.ParentsGuardians;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentsGuardiansRepo extends JpaRepository<ParentsGuardians, Long> {

    List<ParentsGuardians> findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);
}
