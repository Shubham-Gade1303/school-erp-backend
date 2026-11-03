
package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.StudentAchievementRequest;
import com.schooleERP.dto.StudentAchievementResponse;
import com.schooleERP.entity.Student;
import com.schooleERP.entity.StudentAchievement;
import com.schooleERP.repository.StudentAchievementRepo;
import com.schooleERP.repository.StudentRepo;
import com.schooleERP.service.StudentAchievementService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentAchievementServiceImpl
        implements StudentAchievementService {

    private final StudentAchievementRepo achievementRepo;
    private final StudentRepo studentRepo;

    public StudentAchievementServiceImpl(
            StudentAchievementRepo achievementRepo,
            StudentRepo studentRepo) {

        this.achievementRepo = achievementRepo;
        this.studentRepo = studentRepo;
    }

    // CREATE ACHIEVEMENT
    @Override
    public StudentAchievementResponse createAchievement(
            StudentAchievementRequest request) {

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + request.getStudentId()
                ));

        StudentAchievement achievement = new StudentAchievement();

        mapRequestToEntity(request, achievement, student);

        StudentAchievement savedAchievement =
                achievementRepo.save(achievement);

        return mapToResponse(savedAchievement);
    }

    // GET ALL ACHIEVEMENTS
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAllAchievements() {

        return achievementRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ACHIEVEMENT BY ID
    @Override
    @Transactional(readOnly = true)
    public StudentAchievementResponse getAchievementById(Long id) {

        StudentAchievement achievement = achievementRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Achievement not found with id: " + id
                ));

        return mapToResponse(achievement);
    }

    // GET ACHIEVEMENTS BY STUDENT
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAchievementsByStudent(
            Long studentId) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        return achievementRepo.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ACHIEVEMENTS BY TYPE
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAchievementsByType(
            String achievementType) {

        return achievementRepo.findByAchievementType(achievementType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ACHIEVEMENTS BY LEVEL
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAchievementsByLevel(
            String level) {

        return achievementRepo.findByLevel(level)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ACHIEVEMENTS BY POSITION
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAchievementsByPosition(
            String position) {

        return achievementRepo.findByPosition(position)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET ACHIEVEMENTS BY STUDENT AND TYPE
    @Override
    @Transactional(readOnly = true)
    public List<StudentAchievementResponse> getAchievementsByStudentAndType(
            Long studentId,
            String achievementType) {

        if (!studentRepo.existsById(studentId)) {
            throw new IllegalArgumentException(
                    "Student not found with id: " + studentId
            );
        }

        return achievementRepo
                .findByStudentIdAndAchievementType(
                        studentId,
                        achievementType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE ACHIEVEMENT
    @Override
    public StudentAchievementResponse updateAchievement(
            Long id,
            StudentAchievementRequest request) {

        StudentAchievement existingAchievement =
                achievementRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Achievement not found with id: " + id
                        ));

        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found with id: " + request.getStudentId()
                ));

        mapRequestToEntity(
                request,
                existingAchievement,
                student
        );

        StudentAchievement updatedAchievement =
                achievementRepo.save(existingAchievement);

        return mapToResponse(updatedAchievement);
    }

    // DELETE ACHIEVEMENT
    @Override
    public void deleteAchievement(Long id) {

        StudentAchievement achievement =
                achievementRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Achievement not found with id: " + id
                        ));

        achievementRepo.delete(achievement);
    }

    // MAP REQUEST DTO TO ENTITY
    private void mapRequestToEntity(
            StudentAchievementRequest request,
            StudentAchievement achievement,
            Student student) {

        achievement.setStudent(student);
        achievement.setAchievementDate(request.getAchievementDate());
        achievement.setAchievementType(request.getAchievementType());
        achievement.setTitle(request.getTitle());
        achievement.setDescription(request.getDescription());
        achievement.setLevel(request.getLevel());
        achievement.setPosition(request.getPosition());
        achievement.setAwardedBy(request.getAwardedBy());
        achievement.setCertificateUrl(request.getCertificateUrl());
        achievement.setRemarks(request.getRemarks());
    }

    // MAP ENTITY TO RESPONSE DTO
    private StudentAchievementResponse mapToResponse(
            StudentAchievement achievement) {

        Student student = achievement.getStudent();

        StudentAchievementResponse response =
                new StudentAchievementResponse();

        response.setId(achievement.getId());
        response.setStudentId(student.getId());

        response.setStudentName(
                student.getFirstName() + " " + student.getLastName()
        );

        response.setAchievementDate(
                achievement.getAchievementDate()
        );

        response.setAchievementType(
                achievement.getAchievementType()
        );

        response.setTitle(achievement.getTitle());
        response.setDescription(achievement.getDescription());
        response.setLevel(achievement.getLevel());
        response.setPosition(achievement.getPosition());
        response.setAwardedBy(achievement.getAwardedBy());
        response.setCertificateUrl(achievement.getCertificateUrl());
        response.setRemarks(achievement.getRemarks());
        response.setRecordedDate(achievement.getRecordedDate());

        return response;
    }
}
