
        package com.schooleERP.repository;

import com.schooleERP.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySubjectName(String subjectName);

    Optional<Subject> findBySubjectCode(String subjectCode);

    boolean existsBySubjectName(String subjectName);

    boolean existsBySubjectCode(String subjectCode);

    boolean existsBySubjectNameAndIdNot(
            String subjectName,
            Long id
    );

    boolean existsBySubjectCodeAndIdNot(
            String subjectCode,
            Long id
    );
}

