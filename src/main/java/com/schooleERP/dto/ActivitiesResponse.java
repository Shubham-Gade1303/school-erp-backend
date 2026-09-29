package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesResponse {

    private Long id;

    private String activityName;

    private String activityType;

    private String description;

    private String venue;

    private boolean active;
}

