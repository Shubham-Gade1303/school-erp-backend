   package com.schooleERP.repository;

import com.schooleERP.entity.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardRepo
        extends JpaRepository<Standard, Long> {

    Optional<Standard> findByStandardName(String standardName);

    boolean existsByStandardName(String standardName);
}
