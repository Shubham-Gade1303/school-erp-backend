package com.schooleERP.repository;

import com.schooleERP.entity.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMarksRepo extends JpaRepository<StudentMarks, Long> {

    List<StudentMarks> findByStudentId(Long studentId);

    List<StudentMarks> findByExamId(Long examId);

    List<StudentMarks> findBySubjectId(Long subjectId);

    List<StudentMarks> findByStudentIdAndExamId(Long studentId, Long examId);

    List<StudentMarks> findByExamIdAndSubjectId(Long examId, Long subjectId);

    boolean existsByStudentIdAndExamIdAndSubjectId(Long studentId, Long examId, Long subjectId);
}

