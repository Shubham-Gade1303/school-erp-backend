package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentDisciplinaryRecordRequest;
import com.schooleERP.dto.StudentDisciplinaryRecordResponse;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentDisciplinaryRecord;
import com.schooleERP.repository.StudentDisciplinaryRecordRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentDisciplinaryRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentDisciplinaryRecordServiceImpl implements StudentDisciplinaryRecordService {

    private final StudentDisciplinaryRecordRepo disciplinaryRecordRepo;
    private final StudentRepo studentRepo;

    public StudentDisciplinaryRecordServiceImpl(
            StudentDisciplinaryRecordRepo disciplinaryRecordRepo,
            StudentRepo studentRepo
    ) {
        this.disciplinaryRecordRepo = disciplinaryRecordRepo;
        this.studentRepo = studentRepo;
    }

    // CREATE RECORD

    @Override
    public StudentDisciplinaryRecordResponse createRecord(StudentDisciplinaryRecordRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        StudentDisciplinaryRecord record = new StudentDisciplinaryRecord();

        record.setStudent(student);
        record.setIncidentDate(request.getIncidentDate());
        record.setIncidentType(request.getIncidentType());
        record.setDescription(request.getDescription());
        record.setActionTaken(request.getActionTaken());
        record.setRemarks(request.getRemarks());
        record.setResolved(request.isResolved());

        StudentDisciplinaryRecord savedRecord = disciplinaryRecordRepo.save(record);

        return mapToResponse(savedRecord);
    }

    // GET ALL RECORDS

    @Override
    @Transactional(readOnly = true)
    public List<StudentDisciplinaryRecordResponse> getAllRecords() {

        return disciplinaryRecordRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET RECORD BY ID

    @Override
    @Transactional(readOnly = true)
    public StudentDisciplinaryRecordResponse getRecordById(Long id) {
        StudentDisciplinaryRecord record = disciplinaryRecordRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Disciplinary record not found with id: " + id));

        return mapToResponse(record);
    }

    // GET RECORDS BY STUDEN
    @Override
    @Transactional(readOnly = true)
    public List<StudentDisciplinaryRecordResponse>
    getRecordsByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }

        return disciplinaryRecordRepo
                .findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET RECORDS BY INCIDENT TYPE

    @Override
    @Transactional(readOnly = true)
    public List<StudentDisciplinaryRecordResponse>
    getRecordsByIncidentType(String incidentType) {

        return disciplinaryRecordRepo
                .findByIncidentType(incidentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET RECORDS BY RESOLVED STATUS

    @Override
    @Transactional(readOnly = true)
    public List<StudentDisciplinaryRecordResponse>
    getRecordsByResolvedStatus(boolean resolved) {

        return disciplinaryRecordRepo
                .findByResolved(resolved)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY STUDENT + RESOLVED STATUS

    @Override
    @Transactional(readOnly = true)
    public List<StudentDisciplinaryRecordResponse>
    getRecordsByStudentAndResolvedStatus(
            Long studentId,
            boolean resolved
    ) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
        return disciplinaryRecordRepo
                .findByStudentIdAndResolved(studentId, resolved)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE RECORD

    @Override
    public StudentDisciplinaryRecordResponse updateRecord(Long id, StudentDisciplinaryRecordRequest request
    ) {

        StudentDisciplinaryRecord record = disciplinaryRecordRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Disciplinary record not found with id: " + id));

        Student student = studentRepo.findById(request.getStudentId())
                        .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        record.setStudent(student);
        record.setIncidentDate(request.getIncidentDate());
        record.setIncidentType(request.getIncidentType());
        record.setDescription(request.getDescription());
        record.setActionTaken(request.getActionTaken());
        record.setRemarks(request.getRemarks());
        record.setResolved(request.isResolved());

        StudentDisciplinaryRecord updatedRecord = disciplinaryRecordRepo.save(record);
        return mapToResponse(updatedRecord);
    }

    // DELETE RECORD
    @Override
    public void deleteRecord(Long id) {

        StudentDisciplinaryRecord record =
                disciplinaryRecordRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Disciplinary record not found with id: " + id));

        disciplinaryRecordRepo.delete(record);
    }

    // ENTITY → RESPONSE
    private StudentDisciplinaryRecordResponse mapToResponse(
            StudentDisciplinaryRecord record
    ) {
        Student student = record.getStudent();
        StudentDisciplinaryRecordResponse response = new StudentDisciplinaryRecordResponse();
        response.setId(record.getId());
        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());
        response.setStudentName(buildStudentName(student));
        response.setIncidentDate(record.getIncidentDate());
        response.setIncidentType(record.getIncidentType());
        response.setDescription(record.getDescription());
        response.setActionTaken(record.getActionTaken());
        response.setRemarks(record.getRemarks());
        response.setResolved(record.isResolved());
        response.setRecordedDate(record.getRecordedDate());
        return response;
    }

    // BUILD STUDENT NAME

    private String buildStudentName(Student student) {

        StringBuilder name = new StringBuilder();

        if (student.getFirstName() != null && !student.getFirstName().isBlank()) {
            name.append(student.getFirstName());
        }

        if (student.getMiddleName() != null && !student.getMiddleName().isBlank()) {

            if (!name.isEmpty()) {
                name.append(" ");
            }
            name.append(student.getMiddleName());
        }

        if (student.getLastName() != null && !student.getLastName().isBlank()) {

            if (!name.isEmpty()) {
                name.append(" ");
            }
            name.append(student.getLastName());
        }
        return name.toString();
    }
}
