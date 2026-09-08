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
public class ClassSubjectRequest {

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotNull(message = "Standard ID is required")
    private Long standardId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private boolean active = true;
}
