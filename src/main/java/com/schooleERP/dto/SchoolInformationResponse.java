
package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolInformationResponse {

    private Long id;

    private String schoolName;

    private String schoolCode;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String website;

    private String principalName;

    private LocalDate establishedDate;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
