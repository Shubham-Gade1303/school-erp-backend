package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.TeacherRequest;
import com.schooleERP.dto.TeacherResponse;
import com.schooleERP.entity.Teacher;
import com.schooleERP.entity.User;
import com.schooleERP.enums.RoleName;
import com.schooleERP.repository.TeacherRepo;
import com.schooleERP.repository.UserRepo;
import com.schooleERP.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;

    public TeacherServiceImpl(
            TeacherRepo teacherRepo,
            UserRepo userRepo
    ) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
    }

    // =========================
    // CREATE TEACHER
    // =========================

    @Override
    public TeacherResponse createTeacher(
            TeacherRequest request
    ) {

        // Find User
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found with id: "
                                        + request.getUserId()
                        )
                );

        // User must have TEACHER role
        if (user.getRole() != RoleName.TEACHER) {
            throw new IllegalArgumentException(
                    "Selected user does not have TEACHER role"
            );
        }

        // One User can have only one Teacher profile
        if (teacherRepo.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException(
                    "Teacher profile already exists for this user"
            );
        }

        // Employee code must be unique
        if (request.getEmployeeCode() != null
                && !request.getEmployeeCode().isBlank()
                && teacherRepo.existsByEmployeeCode(
                request.getEmployeeCode()
        )) {

            throw new IllegalArgumentException(
                    "Employee code already exists: "
                            + request.getEmployeeCode()
            );
        }

        // Create Teacher
        Teacher teacher = new Teacher();

        teacher.setUser(user);
        teacher.setFullName(request.getFullName());
        teacher.setEmployeeCode(request.getEmployeeCode());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setActive(request.isActive());

        Teacher savedTeacher = teacherRepo.save(teacher);

        return mapToResponse(savedTeacher);
    }

    // =========================
    // GET ALL TEACHERS
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponse> getAllTeachers() {

        return teacherRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET TEACHER BY ID
    // =========================

    @Override
    @Transactional(readOnly = true)
    public TeacherResponse getTeacherById(Long id) {

        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found with id: " + id
                        )
                );

        return mapToResponse(teacher);
    }

    // =========================
    // GET TEACHER BY USER ID
    // =========================

    @Override
    @Transactional(readOnly = true)
    public TeacherResponse getTeacherByUserId(Long userId) {

        Teacher teacher = teacherRepo.findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found for user id: "
                                        + userId
                        )
                );

        return mapToResponse(teacher);
    }

    // =========================
    // UPDATE TEACHER
    // =========================

    @Override
    public TeacherResponse updateTeacher(
            Long id,
            TeacherRequest request
    ) {

        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found with id: " + id
                        )
                );

        // Find User
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found with id: "
                                        + request.getUserId()
                        )
                );

        // User must have TEACHER role
        if (user.getRole() != RoleName.TEACHER) {
            throw new IllegalArgumentException(
                    "Selected user does not have TEACHER role"
            );
        }

        // If changing user, make sure the new user
        // doesn't already have another Teacher profile
        if (!teacher.getUser().getId().equals(request.getUserId())
                && teacherRepo.existsByUserId(request.getUserId())) {

            throw new IllegalArgumentException(
                    "Teacher profile already exists for this user"
            );
        }

        // Check duplicate employee code
        if (request.getEmployeeCode() != null
                && !request.getEmployeeCode().isBlank()
                && teacherRepo.existsByEmployeeCodeAndIdNot(
                request.getEmployeeCode(),
                id
        )) {

            throw new IllegalArgumentException(
                    "Employee code already exists: "
                            + request.getEmployeeCode()
            );
        }

        teacher.setUser(user);
        teacher.setFullName(request.getFullName());
        teacher.setEmployeeCode(request.getEmployeeCode());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setActive(request.isActive());

        Teacher updatedTeacher = teacherRepo.save(teacher);

        return mapToResponse(updatedTeacher);
    }

    // =========================
    // DELETE TEACHER
    // =========================

    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Teacher not found with id: " + id
                        )
                );

        teacherRepo.delete(teacher);
    }

    // =========================
    // ENTITY → RESPONSE
    // =========================

    private TeacherResponse mapToResponse(
            Teacher teacher
    ) {

        TeacherResponse response = new TeacherResponse();

        response.setId(teacher.getId());

        User user = teacher.getUser();

        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        response.setFullName(teacher.getFullName());
        response.setEmployeeCode(teacher.getEmployeeCode());
        response.setPhoneNumber(teacher.getPhoneNumber());
        response.setActive(teacher.isActive());

        return response;
    }
}
