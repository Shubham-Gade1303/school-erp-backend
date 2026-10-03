package com.schooleERP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.schooleERP.entity.StudentActivitiesParticipation;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentActivitiesParticipationRepo extends JpaRepository<StudentActivitiesParticipation , Long> {

    List<StudentActivitiesParticipation > findByStudentId(Long studentId);
    List<StudentActivitiesParticipation > findByActivityId(Long activityId);
    List<StudentActivitiesParticipation > findByParticipationDate(LocalDate participationDate);
    List<StudentActivitiesParticipation > findByStudentIdAndActivityId(Long studentId, Long activityId);

}
