package com.schooleERP.repository;

import com.schooleERP.entity.TeacherClassSubjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassSubjectAssignmentRepo
        extends JpaRepository<TeacherClassSubjectAssignment, Long> {

    List<TeacherClassSubjectAssignment> findByTeacherId(
            Long teacherId
    );

    List<TeacherClassSubjectAssignment> findByClassSectionId(
            Long classSectionId
    );

    List<TeacherClassSubjectAssignment> findByClassSubjectId(
            Long classSubjectId
    );

    List<TeacherClassSubjectAssignment>
    findByTeacherIdAndClassSectionId(
            Long teacherId,
            Long classSectionId
    );

    List<TeacherClassSubjectAssignment>
    findByClassSectionIdAndClassSubjectId(
            Long classSectionId,
            Long classSubjectId
    );

    boolean existsByTeacherIdAndClassSectionIdAndClassSubjectId(
            Long teacherId,
            Long classSectionId,
            Long classSubjectId
    );
}
