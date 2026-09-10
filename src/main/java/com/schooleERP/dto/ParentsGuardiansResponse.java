package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParentsGuardiansResponse {

    private Long id;

    private Long studentId;

    private String admissionNumber;

    private String studentName;

    private String fullName;

    private String relationship;

    private String phoneNumber;

    private String email;

    private String occupation;
}

