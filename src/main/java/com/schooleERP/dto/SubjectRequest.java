package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    @Size(
            max = 100,
            message = "Subject name must not exceed 100 characters"
    )
    private String subjectName;

    @NotBlank(message = "Subject code is required")
    @Size(max = 20,
            message = "Subject code must not exceed 20 characters"
    )
    private String subjectCode;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    private String description;

    private boolean active = true;
}

