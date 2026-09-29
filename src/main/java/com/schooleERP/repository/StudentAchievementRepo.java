package com.schooleERP.repository;

import com.schooleERP.entity.StudentAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAchievementRepo extends JpaRepository<StudentAchievement, Long> {

    List<StudentAchievement> findByStudentId(Long studentId);

    List<StudentAchievement> findByAchievementType(String achievementType);

    List<StudentAchievement> findByLevel(String level);

    List<StudentAchievement> findByPosition(String position);

    List<StudentAchievement> findByStudentIdAndAchievementType(Long studentId, String achievementType );
}
