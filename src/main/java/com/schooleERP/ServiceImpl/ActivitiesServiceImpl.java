package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.ActivitiesRequest;
import com.schooleERP.dto.ActivitiesResponse;
import com.schooleERP.entity.Activities;
import com.schooleERP.repository.ActivitiesRepo;
import com.schooleERP.service.ActivitiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActivitiesServiceImpl implements ActivitiesService {

    private final ActivitiesRepo activitiesRepo;

    public ActivitiesServiceImpl(ActivitiesRepo activitiesRepo) {
        this.activitiesRepo = activitiesRepo;
    }

    @Override
    public ActivitiesResponse createActivity(ActivitiesRequest request) {

        Activities activity = new Activities();

        activity.setActivityName(request.getActivityName());
        activity.setActivityType(request.getActivityType());
        activity.setDescription(request.getDescription());
        activity.setVenue(request.getVenue());
        activity.setActive(request.isActive());

        Activities savedActivity = activitiesRepo.save(activity);

        return mapToResponse(savedActivity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesResponse> getAllActivities() {

        return activitiesRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ActivitiesResponse getActivityById(Long id) {

        Activities activity = activitiesRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Activity not found with id: " + id
                        )
                );

        return mapToResponse(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesResponse> getActivitiesByType(
            String activityType
    ) {

        return activitiesRepo.findByActivityType(activityType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesResponse> getActivitiesByActiveStatus(
            boolean active
    ) {

        return activitiesRepo.findByActive(active)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivitiesResponse> searchActivitiesByName(
            String activityName
    ) {

        return activitiesRepo
                .findByActivityNameContainingIgnoreCase(activityName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ActivitiesResponse updateActivity(
            Long id,
            ActivitiesRequest request
    ) {

        Activities activity = activitiesRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Activity not found with id: " + id
                        )
                );

        activity.setActivityName(request.getActivityName());
        activity.setActivityType(request.getActivityType());
        activity.setDescription(request.getDescription());
        activity.setVenue(request.getVenue());
        activity.setActive(request.isActive());

        Activities updatedActivity = activitiesRepo.save(activity);

        return mapToResponse(updatedActivity);
    }

    @Override
    public void deleteActivity(Long id) {

        Activities activity = activitiesRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Activity not found with id: " + id
                        )
                );

        activitiesRepo.delete(activity);
    }

    private ActivitiesResponse mapToResponse(
            Activities activity
    ) {

        ActivitiesResponse response = new ActivitiesResponse();

        response.setId(activity.getId());
        response.setActivityName(activity.getActivityName());
        response.setActivityType(activity.getActivityType());
        response.setDescription(activity.getDescription());
        response.setVenue(activity.getVenue());
        response.setActive(activity.isActive());

        return response;
    }
}
