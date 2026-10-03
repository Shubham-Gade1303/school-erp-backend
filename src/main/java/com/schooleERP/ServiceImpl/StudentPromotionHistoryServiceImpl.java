package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentPromotionHistoryRequest;
import com.schooleERP.dto.StudentPromotionHistoryResponse;
import com.schooleERP.entity.AcademicYear;
import com.schooleERP.entity.ClassSection;
import com.schooleERP.entity.Standard;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentPromotionHistory;
import com.schooleERP.repository.AcademicYearRepo;
import com.schooleERP.repository.ClassSectionRepo;
import com.schooleERP.repository.StandardRepo;
import com.schooleERP.repository.StudentPromotionHistoryRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentPromotionHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentPromotionHistoryServiceImpl
        implements StudentPromotionHistoryService {

    private final StudentPromotionHistoryRepo promotionHistoryRepo;
    private final StudentRepo studentRepo;
    private final AcademicYearRepo academicYearRepo;
    private final StandardRepo standardRepo;
    private final ClassSectionRepo classSectionRepo;

    public StudentPromotionHistoryServiceImpl(
            StudentPromotionHistoryRepo promotionHistoryRepo,
            StudentRepo studentRepo,
            AcademicYearRepo academicYearRepo,
            StandardRepo standardRepo,
            ClassSectionRepo classSectionRepo) {

        this.promotionHistoryRepo = promotionHistoryRepo;
        this.studentRepo = studentRepo;
        this.academicYearRepo = academicYearRepo;
        this.standardRepo = standardRepo;
        this.classSectionRepo = classSectionRepo;
    }

    @Override
    public StudentPromotionHistoryResponse createPromotion(
            StudentPromotionHistoryRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: "
                                        + request.getStudentId()));

        AcademicYear fromAcademicYear =
                academicYearRepo.findById(request.getFromAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Source academic year not found with id: "
                                                + request.getFromAcademicYearId()));

        Standard fromStandard =
                standardRepo.findById(request.getFromStandardId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Source standard not found with id: " + request.getFromStandardId()));

        ClassSection fromClassSection =
                classSectionRepo.findById(request.getFromClassSectionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Source class section not found with id: "
                                                + request.getFromClassSectionId()));
        AcademicYear toAcademicYear =
                academicYearRepo.findById(request.getToAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Destination academic year not found with id: " + request.getToAcademicYearId()));

        Standard toStandard =
                standardRepo.findById(request.getToStandardId())
                        .orElseThrow(() ->
                                new RuntimeException("Destination standard not found with id: " + request.getToStandardId()));

        ClassSection toClassSection =
                classSectionRepo.findById(request.getToClassSectionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Destination class section not found with id: " + request.getToClassSectionId()));

        if (!"PROMOTED".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Student promotion can only be created with status PROMOTED");
        }

        if (!student.getClassSection().getId()
                .equals(fromClassSection.getId())) {
            throw new RuntimeException("Student is not currently assigned to the source class section");
        }

        student.setClassSection(toClassSection);
        studentRepo.save(student);

        StudentPromotionHistory promotionHistory = new StudentPromotionHistory();

        promotionHistory.setStudent(student);
        promotionHistory.setFromAcademicYear(fromAcademicYear);
        promotionHistory.setFromStandard(fromStandard);
        promotionHistory.setFromClassSection(fromClassSection);
        promotionHistory.setToAcademicYear(toAcademicYear);
        promotionHistory.setToStandard(toStandard);
        promotionHistory.setToClassSection(toClassSection);
        promotionHistory.setStatus(request.getStatus());
        promotionHistory.setRemarks(request.getRemarks());

        StudentPromotionHistory savedPromotion = promotionHistoryRepo.save(promotionHistory);
        return mapToResponse(savedPromotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPromotionHistoryResponse> getAllPromotions() {

        return promotionHistoryRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPromotionHistoryResponse getPromotionById(Long id) {

        StudentPromotionHistory promotion =
                promotionHistoryRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Promotion history not found with id: " + id));
        return mapToResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPromotionHistoryResponse> getPromotionsByStudent(Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }

        return promotionHistoryRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPromotionHistoryResponse>
    getPromotionsByFromAcademicYear(Long academicYearId) {

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new RuntimeException("Academic year not found with id: " + academicYearId);
        }

        return promotionHistoryRepo
                .findByFromAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPromotionHistoryResponse>
    getPromotionsByToAcademicYear(Long academicYearId) {

        if (!academicYearRepo.existsById(academicYearId)) {
            throw new RuntimeException("Academic year not found with id: " + academicYearId);
        }

        return promotionHistoryRepo
                .findByToAcademicYearId(academicYearId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentPromotionHistoryResponse>
    getPromotionsByStatus(String status) {

        return promotionHistoryRepo.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentPromotionHistoryResponse updatePromotion(Long id, StudentPromotionHistoryRequest request) {

        StudentPromotionHistory promotion =
                promotionHistoryRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Promotion history not found with id: " + id));

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        AcademicYear fromAcademicYear =
                academicYearRepo.findById(request.getFromAcademicYearId())
                        .orElseThrow(() -> new RuntimeException("Source academic year not found with id: " + request.getFromAcademicYearId()));

        Standard fromStandard =
                standardRepo.findById(request.getFromStandardId())
                        .orElseThrow(() -> new RuntimeException("Source standard not found with id: " + request.getFromStandardId()));

        ClassSection fromClassSection =
                classSectionRepo.findById(request.getFromClassSectionId())
                        .orElseThrow(() -> new RuntimeException("Source class section not found with id: " + request.getFromClassSectionId()));

        AcademicYear toAcademicYear =
                academicYearRepo.findById(request.getToAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Destination academic year not found with id: " + request.getToAcademicYearId()));

        Standard toStandard =
                standardRepo.findById(request.getToStandardId())
                        .orElseThrow(() ->
                                new RuntimeException("Destination standard not found with id: " + request.getToStandardId()));

        ClassSection toClassSection =
                classSectionRepo.findById(request.getToClassSectionId())
                        .orElseThrow(() -> new RuntimeException("Destination class section not found with id: " + request.getToClassSectionId()));

        if (!"PROMOTED".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Promotion history status must be PROMOTED");
        }

        promotion.setStudent(student);
        promotion.setFromAcademicYear(fromAcademicYear);
        promotion.setFromStandard(fromStandard);
        promotion.setFromClassSection(fromClassSection);
        promotion.setToAcademicYear(toAcademicYear);
        promotion.setToStandard(toStandard);
        promotion.setToClassSection(toClassSection);
        promotion.setStatus(request.getStatus());
        promotion.setRemarks(request.getRemarks());

        StudentPromotionHistory updatedPromotion = promotionHistoryRepo.save(promotion);
        student.setClassSection(toClassSection);
        studentRepo.save(student);
        return mapToResponse(updatedPromotion);
    }

    @Override
    public void deletePromotion(Long id) {
        StudentPromotionHistory promotion =
                promotionHistoryRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Promotion history not found with id: " + id));

        promotionHistoryRepo.delete(promotion);
    }

    private StudentPromotionHistoryResponse mapToResponse(
            StudentPromotionHistory promotion) {

        StudentPromotionHistoryResponse response = new StudentPromotionHistoryResponse();
        response.setId(promotion.getId());
        Student student = promotion.getStudent();
        response.setStudentId(student.getId());
        response.setStudentName(buildStudentName(student));
        AcademicYear fromAcademicYear = promotion.getFromAcademicYear();
        response.setFromAcademicYearId(fromAcademicYear.getId());
        response.setFromAcademicYear(fromAcademicYear.getAcademicYear());
        Standard fromStandard = promotion.getFromStandard();
        response.setFromStandardId(fromStandard.getId());
        response.setFromStandard(fromStandard.getStandardName());
        ClassSection fromClassSection = promotion.getFromClassSection();
        response.setFromClassSectionId(fromClassSection.getId());
        response.setFromSection(fromClassSection.getSectionName());
        AcademicYear toAcademicYear = promotion.getToAcademicYear();
        response.setToAcademicYearId(toAcademicYear.getId());
        response.setToAcademicYear(toAcademicYear.getAcademicYear());
        Standard toStandard = promotion.getToStandard();
        response.setToStandardId(toStandard.getId());
        response.setToStandard(toStandard.getStandardName());
        ClassSection toClassSection = promotion.getToClassSection();
        response.setToClassSectionId(toClassSection.getId());
        response.setToSection(toClassSection.getSectionName());
        response.setPromotionDate(promotion.getPromotionDate());
        response.setStatus(promotion.getStatus());
        response.setRemarks(promotion.getRemarks());

        return response;
    }

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
