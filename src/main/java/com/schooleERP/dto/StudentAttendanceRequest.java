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
public class StudentAttendanceRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private LocalDate attendanceDate;

    @NotBlank
    @Size(max = 20)
    private String status;

    @Size(max = 200)
    private String remarks;
}
