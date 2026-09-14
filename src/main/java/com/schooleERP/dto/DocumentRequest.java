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
public class DocumentRequest {

    @NotNull
    private Long studentId;

    @NotBlank
    @Size(max = 100)
    private String documentType;

    @NotBlank
    @Size(max = 255)
    private String documentName;

    @Size(max = 500)
    private String documentUrl;


    private LocalDate uploadedDate;
}


