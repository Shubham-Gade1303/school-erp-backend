package com.schooleERP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesRequest {

    @NotBlank
    @Size(max = 200)
    private String activityName;

    @NotBlank
    @Size(max = 100)
    private String activityType;

    @Size(max = 1000)
    private String description;

    @Size(max = 200)
    private String venue;

    private boolean active;
}
