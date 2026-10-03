package com.schooleERP.controller;

import com.schooleERP.dto.ActivitiesRequest;
import com.schooleERP.dto.ActivitiesResponse;
import com.schooleERP.service.ActivitiesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    public ActivitiesController(ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ActivitiesResponse> createActivity(
            @Valid @RequestBody ActivitiesRequest request
    ) {
        ActivitiesResponse response =
                activitiesService.createActivity(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ActivitiesResponse>> getAllActivities() {

        return ResponseEntity.ok(
                activitiesService.getAllActivities()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<ActivitiesResponse> getActivityById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                activitiesService.getActivityById(id)
        );
    }

    @GetMapping("/type/{activityType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ActivitiesResponse>> getActivitiesByType(
            @PathVariable String activityType
    ) {
        return ResponseEntity.ok(
                activitiesService.getActivitiesByType(activityType)
        );
    }

    @GetMapping("/active/{active}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ActivitiesResponse>> getActivitiesByActiveStatus(
            @PathVariable boolean active
    ) {
        return ResponseEntity.ok(
                activitiesService.getActivitiesByActiveStatus(active)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<ActivitiesResponse>> searchActivitiesByName(
            @RequestParam String activityName
    ) {
        return ResponseEntity.ok(
                activitiesService.searchActivitiesByName(activityName)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<ActivitiesResponse> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody ActivitiesRequest request
    ) {
        return ResponseEntity.ok(
                activitiesService.updateActivity(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable Long id
    ) {
        activitiesService.deleteActivity(id);

        return ResponseEntity.noContent().build();
    }
}

