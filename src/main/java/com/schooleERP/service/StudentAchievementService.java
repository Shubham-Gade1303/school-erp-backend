
package com.schooleERP.service;

import com.schooleERP.dto.StudentAchievementRequest;
import com.schooleERP.dto.StudentAchievementResponse;


import java.util.List;

public interface StudentAchievementService {

    StudentAchievementResponse createAchievement(
            StudentAchievementRequest request
    );

    List<StudentAchievementResponse> getAllAchievements();

    StudentAchievementResponse getAchievementById(Long id);

    List<StudentAchievementResponse> getAchievementsByStudent(Long studentId);

    List<StudentAchievementResponse> getAchievementsByType(String achievementType);

    List<StudentAchievementResponse> getAchievementsByLevel(String level);

    List<StudentAchievementResponse> getAchievementsByPosition(String position);

    List<StudentAchievementResponse> getAchievementsByStudentAndType(
            Long studentId,
            String achievementType
    );

    StudentAchievementResponse updateAchievement(
            Long id,
            StudentAchievementRequest request
    );

    void deleteAchievement(Long id);
}
