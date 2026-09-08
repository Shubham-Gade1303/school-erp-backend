package com.schooleERP.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class SchoolInformationRequest {

    @NotBlank(message = "School name is required")
    @Size(max = 150, message = "School name must not exceed 150 characters")
    private String schoolName;

    @NotBlank(message = "School code is required")
    @Size(max = 50, message = "School code must not exceed 50 characters")
    private String schoolCode;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @Size(max = 10, message = "Pincode must not exceed 10 characters")
    private String pincode;

    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;

    @Size(max = 150, message = "Principal name must not exceed 150 characters")
    private String principalName;

    private LocalDate establishedDate;

}

