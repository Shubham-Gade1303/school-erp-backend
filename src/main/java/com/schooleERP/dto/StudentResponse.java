package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;

    private String admissionNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String bloodGroup;

    private Long classSectionId;

    private String sectionName;

    private String standardName;

    private String academicYear;

    private boolean active;
}
