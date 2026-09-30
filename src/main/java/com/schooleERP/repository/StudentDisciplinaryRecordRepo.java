package com.schooleERP.repository;

import com.schooleERP.entity.StudentDisciplinaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDisciplinaryRecordRepo extends JpaRepository<StudentDisciplinaryRecord, Long> {

    List<StudentDisciplinaryRecord> findByStudentId(Long studentId);

    List<StudentDisciplinaryRecord> findByIncidentType(String incidentType);

    List<StudentDisciplinaryRecord> findByResolved(boolean resolved);

    List<StudentDisciplinaryRecord> findByStudentIdAndResolved(Long studentId, boolean resolved);

}