package com.schooleERP.dto;

import jakarta.validation.constraints.Email;
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
public class ParentsGuardiansRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Parent/Guardian name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Relationship is required")
    @Size(max = 30, message = "Relationship must not exceed 30 characters")
    private String relationship;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 100, message = "Occupation must not exceed 100 characters")
    private String occupation;
}

