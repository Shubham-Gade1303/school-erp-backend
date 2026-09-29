package com.schooleERP.service;

import com.schooleERP.dto.StudentDisciplinaryRecordRequest;
import com.schooleERP.dto.StudentDisciplinaryRecordResponse;

import java.util.List;

public interface StudentDisciplinaryRecordService {

    StudentDisciplinaryRecordResponse createRecord(StudentDisciplinaryRecordRequest request);

    List<StudentDisciplinaryRecordResponse> getAllRecords();

    StudentDisciplinaryRecordResponse getRecordById(Long id);

    List<StudentDisciplinaryRecordResponse> getRecordsByStudent(Long studentId);

    List<StudentDisciplinaryRecordResponse> getRecordsByIncidentType(String incidentType);

    List<StudentDisciplinaryRecordResponse> getRecordsByResolvedStatus(boolean resolved);

    List<StudentDisciplinaryRecordResponse> getRecordsByStudentAndResolvedStatus(Long studentId, boolean resolved);

    StudentDisciplinaryRecordResponse updateRecord(Long id, StudentDisciplinaryRecordRequest request);

    void deleteRecord(Long id);
}
