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
public class ExamRequest {

    @NotBlank
    @Size(max = 100)
    private String examName;

    @NotNull
    private Long academicYearId;

    @NotNull
    private Long standardId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private boolean active = true;
}

