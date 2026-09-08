package com.schooleERP.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardRequest {

    @NotBlank(message = "Standard name is required")
    @Size(
            max = 50,
            message = "Standard name must not exceed 50 characters"
    )
    private String standardName;

    @NotNull(message = "Display order is required")
    @Min(
            value = 1,
            message = "Display order must be greater than 0"
    )
    private Integer displayOrder;

    private boolean active = true;
}

