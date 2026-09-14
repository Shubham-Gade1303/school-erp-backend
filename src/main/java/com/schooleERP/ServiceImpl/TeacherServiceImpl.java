package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.TeacherRequest;
import com.schooleERP.dto.TeacherResponse;
import com.schooleERP.entity.Teacher;
import com.schooleERP.entity.User;
import com.schooleERP.enums.RoleName;
import com.schooleERP.repository.TeacherRepo;
import com.schooleERP.repository.UserRepo;
import com.schooleERP.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImpl(
            TeacherRepo teacherRepo,
            UserRepo userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // CREATE TEACHER
    // =========================

    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {

        // Check username
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    "Username already exists"
            );
        }

        // Check email
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        // Check employee code
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

        // =========================
        // CREATE USER
        // =========================

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Automatically assign TEACHER role
        user.setRole(RoleName.TEACHER);

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setEnabled(request.isActive());

        User savedUser = userRepo.save(user);

        // =========================
        // CREATE TEACHER
        // =========================

        Teacher teacher = new Teacher();

        // Connect Teacher with User
        teacher.setUser(savedUser);

        teacher.setFullName(
                request.getFullName()
        );

        teacher.setEmployeeCode(
                request.getEmployeeCode()
        );

        teacher.setPhoneNumber(
                request.getPhoneNumber()
        );

        teacher.setActive(
                request.isActive()
        );

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

        // Check employee code
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

        // =========================
        // UPDATE TEACHER
        // =========================

        teacher.setFullName(
                request.getFullName()
        );

        teacher.setEmployeeCode(
                request.getEmployeeCode()
        );

        teacher.setPhoneNumber(
                request.getPhoneNumber()
        );

        teacher.setActive(
                request.isActive()
        );

        // =========================
        // UPDATE USER
        // =========================

        User user = teacher.getUser();

        user.setUsername(
                request.getUsername()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setEnabled(
                request.isActive()
        );

        // Update password only if provided
        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }

        Teacher updatedTeacher =
                teacherRepo.save(teacher);

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

        TeacherResponse response =
                new TeacherResponse();

        response.setId(
                teacher.getId()
        );

        User user = teacher.getUser();

        response.setUserId(
                user.getId()
        );

        response.setUsername(
                user.getUsername()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setFullName(
                teacher.getFullName()
        );

        response.setEmployeeCode(
                teacher.getEmployeeCode()
        );

        response.setPhoneNumber(
                teacher.getPhoneNumber()
        );

        response.setActive(
                teacher.isActive()
        );

        return response;
    }
}