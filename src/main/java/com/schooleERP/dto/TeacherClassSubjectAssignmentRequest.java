package com.schooleERP.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassSubjectAssignmentRequest {

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Class section ID is required")
    private Long classSectionId;

    @NotNull(message = "Class subject ID is required")
    private Long classSubjectId;

    private boolean active = true;
}

