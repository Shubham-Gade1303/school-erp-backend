package com.schooleERP.service;

import com.schooleERP.dto.TeacherRequest;
import com.schooleERP.dto.TeacherResponse;

import java.util.List;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    List<TeacherResponse> getAllTeachers();

    TeacherResponse getTeacherById(Long id);

    TeacherResponse getTeacherByUserId(Long userId);

    TeacherResponse updateTeacher(
            Long id,
            TeacherRequest request
    );

    void deleteTeacher(Long id);
}
