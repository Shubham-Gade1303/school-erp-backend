package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Admission number is required")
    @Size(max = 30, message = "Admission number must not exceed 30 characters")
    private String admissionNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    @Size(max = 20, message = "Gender must not exceed 20 characters")
    private String gender;

    @Size(max = 20, message = "Blood group must not exceed 20 characters")
    private String bloodGroup;

    @NotNull(message = "Class section is required")
    private Long classSectionId;

    private boolean active = true;
}

