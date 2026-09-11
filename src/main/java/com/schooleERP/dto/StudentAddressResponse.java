package com.schooleERP.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAddressResponse {

    private Long id;

    private Long studentId;
    private String admissionNumber;
    private String studentName;

    private String addressLine;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}

