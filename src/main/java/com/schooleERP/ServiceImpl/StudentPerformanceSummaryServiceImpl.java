package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentPerformanceSummaryRequest;
import com.schooleERP.dto.StudentPerformanceSummaryResponse;
import com.schooleERP.entity.Exam;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentMarks;
import com.schooleERP.entity.StudentPerformanceSummary;
import com.schooleERP.repository.ExamRepo;
import com.schooleERP.repository.StudentMarksRepo;
import com.schooleERP.repository.StudentPerformanceSummaryRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentPerformanceSummaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentPerformanceSummaryServiceImpl
        implements StudentPerformanceSummaryService {

    private final StudentPerformanceSummaryRepo summaryRepo;
    private final StudentRepo studentRepo;
    private final ExamRepo examRepo;
    private final StudentMarksRepo studentMarksRepo;

    public StudentPerformanceSummaryServiceImpl(StudentPerformanceSummaryRepo summaryRepo,
                                                StudentRepo studentRepo, ExamRepo examRepo,
                                                StudentMarksRepo studentMarksRepo) {

        this.summaryRepo = summaryRepo;
        this.studentRepo = studentRepo;
        this.examRepo = examRepo;
        this.studentMarksRepo = studentMarksRepo;
    }

    @Override
    public StudentPerformanceSummaryResponse generateSummary(
            StudentPerformanceSummaryRequest request) {

        Student student = studentRepo.findById(request.getStudentId()).orElseThrow(() ->
                new IllegalArgumentException("Student not found with id: " + request.getStudentId()));

        Exam exam = examRepo.findById(request.getExamId()).orElseThrow(() ->
                        new IllegalArgumentException("Exam not found with id: " + request.getExamId()));

        boolean exists = summaryRepo.existsByStudentIdAndExamId(request.getStudentId(), request.getExamId()
        );

        if (exists) {
            throw new IllegalArgumentException("Performance summary already exists for this student and exam");
        }

        List<StudentMarks> marksList =
                studentMarksRepo.findByStudentIdAndExamId(request.getStudentId(), request.getExamId());

        if (marksList.isEmpty()) {
            throw new IllegalArgumentException("No marks found for this student and exam");
        }

        double totalMarks = 0.0;
        double marksObtained = 0.0;

        for (StudentMarks marks : marksList) {

            totalMarks += marks.getMaximumMarks();
            marksObtained += marks.getMarksObtained();
        }

        double percentage =
                (marksObtained / totalMarks) * 100;

        String grade = calculateGrade(percentage);

        String resultStatus = percentage >= 35.0 ? "PASS" : "FAIL";

        StudentPerformanceSummary summary =
                new StudentPerformanceSummary();

        summary.setStudent(student);
        summary.setExam(exam);
        summary.setTotalMarks(totalMarks);
        summary.setMarksObtained(marksObtained);
        summary.setPercentage(percentage);
        summary.setGrade(grade);
        summary.setResultStatus(resultStatus);

        StudentPerformanceSummary savedSummary =
                summaryRepo.save(summary);

        return mapToResponse(savedSummary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPerformanceSummaryResponse> getAllSummaries() {

        return summaryRepo.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPerformanceSummaryResponse getSummaryById(Long id) {

        StudentPerformanceSummary summary = summaryRepo.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Performance summary not found with id: " + id));

        return mapToResponse(summary);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPerformanceSummaryResponse
    getSummaryByStudentAndExam(
            Long studentId,
            Long examId) {

        StudentPerformanceSummary summary =
                summaryRepo.findByStudentIdAndExamId(
                                studentId,
                                examId
                        )
                        .orElseThrow(() ->
                                new IllegalArgumentException("Performance summary not found for student " + studentId + " and exam " + examId));
        return mapToResponse(summary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPerformanceSummaryResponse>
    getSummariesByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }

        return summaryRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPerformanceSummaryResponse>
    getSummariesByExam(Long examId) {

        if (!examRepo.existsById(examId)) {
            throw new IllegalArgumentException("Exam not found with id: " + examId);
        }

        return summaryRepo.findByExamId(examId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentPerformanceSummaryResponse updateSummary(
            Long id, StudentPerformanceSummaryRequest request) {

        StudentPerformanceSummary existingSummary =
                summaryRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Performance summary not found with id: " + id));

        Student student = studentRepo.findById(
                        request.getStudentId()
                )
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + request.getStudentId()));
        Exam exam = examRepo.findById(
                        request.getExamId()
                )
                .orElseThrow(() ->
                        new IllegalArgumentException("Exam not found with id: " + request.getExamId()));

        boolean duplicate =
                summaryRepo.existsByStudentIdAndExamId(
                        request.getStudentId(),
                        request.getExamId()
                );

        if (duplicate
                && !(existingSummary.getStudent().getId()
                .equals(request.getStudentId())
                && existingSummary.getExam().getId()
                .equals(request.getExamId()))) {

            throw new IllegalArgumentException("Performance summary already exists for this student and exam");
        }

        List<StudentMarks> marksList =
                studentMarksRepo.findByStudentIdAndExamId(
                        request.getStudentId(),
                        request.getExamId()
                );

        if (marksList.isEmpty()) {
            throw new IllegalArgumentException("No marks found for this student and exam");
        }

        double totalMarks = 0.0;
        double marksObtained = 0.0;

        for (StudentMarks marks : marksList) {

            totalMarks += marks.getMaximumMarks();
            marksObtained += marks.getMarksObtained();
        }

        double percentage =
                (marksObtained / totalMarks) * 100;

        String grade = calculateGrade(percentage);

        String resultStatus =
                percentage >= 35.0 ? "PASS" : "FAIL";

        existingSummary.setStudent(student);
        existingSummary.setExam(exam);
        existingSummary.setTotalMarks(totalMarks);
        existingSummary.setMarksObtained(marksObtained);
        existingSummary.setPercentage(percentage);
        existingSummary.setGrade(grade);
        existingSummary.setResultStatus(resultStatus);

        StudentPerformanceSummary updatedSummary =
                summaryRepo.save(existingSummary);

        return mapToResponse(updatedSummary);
    }

    @Override
    public void deleteSummary(Long id) {

        StudentPerformanceSummary summary =
                summaryRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Performance summary not found with id: " + id));

        summaryRepo.delete(summary);
    }

    private String calculateGrade(double percentage) {

        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B+";
        } else if (percentage >= 60) {
            return "B";
        } else if (percentage >= 50) {
            return "C+";
        } else if (percentage >= 40) {
            return "C";
        } else if (percentage >= 35) {
            return "D";
        } else {
            return "F";
        }
    }

    private StudentPerformanceSummaryResponse mapToResponse(
            StudentPerformanceSummary summary) {

        Student student = summary.getStudent();
        Exam exam = summary.getExam();

        String studentName =
                student.getFirstName()
                        + " "
                        + student.getLastName();

        StudentPerformanceSummaryResponse response =
                new StudentPerformanceSummaryResponse();

        response.setId(summary.getId());

        response.setStudentId(student.getId());
        response.setAdmissionNumber(
                student.getAdmissionNumber()
        );
        response.setStudentName(studentName);

        response.setExamId(exam.getId());
        response.setExamName(exam.getExamName());

        response.setTotalMarks(summary.getTotalMarks());
        response.setMarksObtained(
                summary.getMarksObtained()
        );
        response.setPercentage(
                summary.getPercentage()
        );
        response.setGrade(summary.getGrade());
        response.setResultStatus(
                summary.getResultStatus()
        );

        return response;
    }
}
