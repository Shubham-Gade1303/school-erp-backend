package com.schooleERP.service;
import com.schooleERP.dto.StudentActivitiesParticipationRequest;
import com.schooleERP.dto.StudentActivitiesParticipationResponse;
import java.time.LocalDate;
import java.util.List;
public interface StudentActivitiesParticipationService {

    StudentActivitiesParticipationResponse createParticipation(StudentActivitiesParticipationRequest request);

    List<StudentActivitiesParticipationResponse> getAllParticipations();

    StudentActivitiesParticipationResponse getParticipationById(Long id);

    List<StudentActivitiesParticipationResponse> getParticipationsByStudent(Long studentId);

    List<StudentActivitiesParticipationResponse> getParticipationsByActivity(Long activityId);

    List<StudentActivitiesParticipationResponse> getParticipationsByDate(LocalDate participationDate);

    StudentActivitiesParticipationResponse updateParticipation(Long id, StudentActivitiesParticipationRequest request);

    void deleteParticipation(Long id);
}

