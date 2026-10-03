package com.schooleERP.service;

import com.schooleERP.dto.StudentPromotionHistoryRequest;
import com.schooleERP.dto.StudentPromotionHistoryResponse;

import java.util.List;

public interface StudentPromotionHistoryService {

    StudentPromotionHistoryResponse createPromotion(StudentPromotionHistoryRequest request);

    List<StudentPromotionHistoryResponse> getAllPromotions();

    StudentPromotionHistoryResponse getPromotionById(Long id);

    List<StudentPromotionHistoryResponse> getPromotionsByStudent(Long studentId);

    List<StudentPromotionHistoryResponse> getPromotionsByFromAcademicYear(Long academicYearId);

    List<StudentPromotionHistoryResponse> getPromotionsByToAcademicYear(Long academicYearId);

    List<StudentPromotionHistoryResponse> getPromotionsByStatus(String status);

    StudentPromotionHistoryResponse updatePromotion(Long id, StudentPromotionHistoryRequest request);

    void deletePromotion(Long id);
}