package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {

    private Long id;

    private Long userId;
    private String username;
    private String email;

    private String fullName;
    private String employeeCode;
    private String phoneNumber;

    private boolean active;
}
