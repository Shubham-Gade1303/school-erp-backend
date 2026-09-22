package com.schooleERP.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeesRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long academicYearId;

    @NotBlank
    @Size(max = 50)
    private String feeType;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private LocalDate dueDate;

    @NotBlank
    @Size(max = 20)
    private String status;

    @Size(max = 500)
    private String remarks;
}
