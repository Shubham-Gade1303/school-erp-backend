package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentMarksRequest;
import com.schooleERP.dto.StudentMarksResponse;
import com.schooleERP.entity.Exam;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentMarks;
import com.schooleERP.entity.Subject;
import com.schooleERP.repository.ExamRepo;
import com.schooleERP.repository.StudentMarksRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.repository.SubjectRepo;
import com.schooleERP.service.StudentMarksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentMarksServiceImpl implements StudentMarksService {

    private final StudentMarksRepo studentMarksRepo;
    private final StudentRepo studentRepo;
    private final ExamRepo examRepo;
    private final SubjectRepo subjectRepo;

    public StudentMarksServiceImpl(
            StudentMarksRepo studentMarksRepo,
            StudentRepo studentRepo,
            ExamRepo examRepo,
            SubjectRepo subjectRepo) {

        this.studentMarksRepo = studentMarksRepo;
        this.studentRepo = studentRepo;
        this.examRepo = examRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public StudentMarksResponse addMarks(StudentMarksRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        Exam exam = examRepo.findById(request.getExamId()).orElseThrow(() -> new IllegalArgumentException("Exam not found with id: " + request.getExamId()));

        Subject subject = subjectRepo.findById(request.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Subject not found with id: " + request.getSubjectId()));

        if (request.getMarksObtained() > request.getMaximumMarks()) {
            throw new IllegalArgumentException("Marks obtained cannot be greater than maximum marks");
        }

        boolean exists =
                studentMarksRepo.existsByStudentIdAndExamIdAndSubjectId(
                        request.getStudentId(),
                        request.getExamId(),
                        request.getSubjectId()
                );

        if (exists) {
            throw new IllegalArgumentException(
                    "Marks already exist for this student, exam and subject"
            );
        }

        StudentMarks studentMarks = new StudentMarks();

        studentMarks.setStudent(student);
        studentMarks.setExam(exam);
        studentMarks.setSubject(subject);
        studentMarks.setMarksObtained(request.getMarksObtained());
        studentMarks.setMaximumMarks(request.getMaximumMarks());
        studentMarks.setRemarks(request.getRemarks());

        StudentMarks savedMarks = studentMarksRepo.save(studentMarks);

        return mapToResponse(savedMarks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getAllMarks() {

        return studentMarksRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentMarksResponse getMarksById(Long id) {

        StudentMarks studentMarks = studentMarksRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Marks not found with id: " + id
                        ));

        return mapToResponse(studentMarks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getMarksByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        return studentMarksRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getMarksByExam(Long examId) {

        if (!examRepo.existsById(examId)) {
            throw new IllegalArgumentException(
                    "Exam not found with id: " + examId
            );
        }

        return studentMarksRepo.findByExamId(examId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getMarksBySubject(Long subjectId) {

        if (!subjectRepo.existsById(subjectId)) {
            throw new IllegalArgumentException(
                    "Subject not found with id: " + subjectId
            );
        }

        return studentMarksRepo.findBySubjectId(subjectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getMarksByStudentAndExam(
            Long studentId,
            Long examId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        if (!examRepo.existsById(examId)) {
            throw new IllegalArgumentException(
                    "Exam not found with id: " + examId
            );
        }

        return studentMarksRepo
                .findByStudentIdAndExamId(studentId, examId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentMarksResponse> getMarksByExamAndSubject(
            Long examId,
            Long subjectId) {

        if (!examRepo.existsById(examId)) {
            throw new IllegalArgumentException(
                    "Exam not found with id: " + examId
            );
        }

        if (!subjectRepo.existsById(subjectId)) {
            throw new IllegalArgumentException(
                    "Subject not found with id: " + subjectId
            );
        }

        return studentMarksRepo
                .findByExamIdAndSubjectId(examId, subjectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentMarksResponse updateMarks(
            Long id,
            StudentMarksRequest request) {

        StudentMarks existingMarks = studentMarksRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Marks not found with id: " + id
                        ));

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found with id: " + request.getStudentId()
                        ));

        Exam exam = examRepo.findById(request.getExamId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Exam not found with id: " + request.getExamId()
                        ));

        Subject subject = subjectRepo.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Subject not found with id: " + request.getSubjectId()
                        ));

        if (request.getMarksObtained() > request.getMaximumMarks()) {
            throw new IllegalArgumentException(
                    "Marks obtained cannot be greater than maximum marks"
            );
        }

        boolean duplicate =
                studentMarksRepo.existsByStudentIdAndExamIdAndSubjectId(
                        request.getStudentId(),
                        request.getExamId(),
                        request.getSubjectId()
                );

        if (duplicate
                && !(existingMarks.getStudent().getId().equals(request.getStudentId())
                && existingMarks.getExam().getId().equals(request.getExamId())
                && existingMarks.getSubject().getId().equals(request.getSubjectId()))) {

            throw new IllegalArgumentException(
                    "Marks already exist for this student, exam and subject"
            );
        }

        existingMarks.setStudent(student);
        existingMarks.setExam(exam);
        existingMarks.setSubject(subject);
        existingMarks.setMarksObtained(request.getMarksObtained());
        existingMarks.setMaximumMarks(request.getMaximumMarks());
        existingMarks.setRemarks(request.getRemarks());

        StudentMarks updatedMarks = studentMarksRepo.save(existingMarks);

        return mapToResponse(updatedMarks);
    }

    @Override
    public void deleteMarks(Long id) {

        StudentMarks studentMarks = studentMarksRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Marks not found with id: " + id
                        ));

        studentMarksRepo.delete(studentMarks);
    }

    private StudentMarksResponse mapToResponse(StudentMarks studentMarks) {

        Student student = studentMarks.getStudent();
        Exam exam = studentMarks.getExam();
        Subject subject = studentMarks.getSubject();

        String studentName =
                student.getFirstName() + " " + student.getLastName();

        StudentMarksResponse response = new StudentMarksResponse();

        response.setId(studentMarks.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(student.getAdmissionNumber());
        response.setStudentName(studentName);

        response.setExamId(exam.getId());
        response.setExamName(exam.getExamName());

        response.setSubjectId(subject.getId());
        response.setSubjectName(subject.getSubjectName());
        response.setSubjectCode(subject.getSubjectCode());

        response.setMarksObtained(studentMarks.getMarksObtained());
        response.setMaximumMarks(studentMarks.getMaximumMarks());
        response.setRemarks(studentMarks.getRemarks());

        return response;
    }
}

