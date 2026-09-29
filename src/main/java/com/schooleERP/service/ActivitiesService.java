package com.schooleERP.service;

import com.schooleERP.dto.ActivitiesRequest;
import com.schooleERP.dto.ActivitiesResponse;

import java.util.List;

public interface ActivitiesService {

    ActivitiesResponse createActivity(ActivitiesRequest request);

    List<ActivitiesResponse> getAllActivities();

    ActivitiesResponse getActivityById(Long id);

    List<ActivitiesResponse> getActivitiesByType(String activityType);

    List<ActivitiesResponse> getActivitiesByActiveStatus(boolean active);

    List<ActivitiesResponse> searchActivitiesByName(String activityName);

    ActivitiesResponse updateActivity(Long id, ActivitiesRequest request);

    void deleteActivity(Long id);
}

