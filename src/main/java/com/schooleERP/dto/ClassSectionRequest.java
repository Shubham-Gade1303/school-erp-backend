 package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSectionRequest {

    @NotNull(message = "Academic year ID is required")
    private Long academicYearId;

    @NotNull(message = "Standard ID is required")
    private Long standardId;

    @NotBlank(message = "Section name is required")
    @Size(
            max = 10,
            message = "Section name must not exceed 10 characters"
    )
    private String sectionName;

    private boolean active = true;
}

