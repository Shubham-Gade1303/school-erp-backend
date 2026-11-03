
package com.schooleERP.controller;

import com.schooleERP.dto.StudentAchievementRequest;
import com.schooleERP.dto.StudentAchievementResponse;
import com.schooleERP.service.StudentAchievementService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-achievements")
public class StudentAchievementController {

    private final StudentAchievementService achievementService;

    public StudentAchievementController(
            StudentAchievementService achievementService) {

        this.achievementService = achievementService;
    }

    // CREATE ACHIEVEMENT
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAchievementResponse> createAchievement(
            @Valid @RequestBody StudentAchievementRequest request) {

        StudentAchievementResponse response =
                achievementService.createAchievement(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET ALL ACHIEVEMENTS
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAllAchievements() {

        return ResponseEntity.ok(
                achievementService.getAllAchievements()
        );
    }

    // GET ACHIEVEMENT BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAchievementResponse> getAchievementById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                achievementService.getAchievementById(id)
        );
    }

    // GET ACHIEVEMENTS BY STUDENT
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAchievementsByStudent(@PathVariable Long studentId) {

        return ResponseEntity.ok(
                achievementService.getAchievementsByStudent(studentId)
        );
    }

    // GET ACHIEVEMENTS BY TYPE
    @GetMapping("/type/{achievementType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAchievementsByType(@PathVariable String achievementType) {

        return ResponseEntity.ok(
                achievementService.getAchievementsByType(achievementType)
        );
    }

    // GET ACHIEVEMENTS BY LEVEL
    @GetMapping("/level/{level}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAchievementsByLevel(@PathVariable String level) {

        return ResponseEntity.ok(
                achievementService.getAchievementsByLevel(level)
        );
    }

    // GET ACHIEVEMENTS BY POSITION
    @GetMapping("/position/{position}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAchievementsByPosition(@PathVariable String position) {

        return ResponseEntity.ok(
                achievementService.getAchievementsByPosition(position)
        );
    }

    // GET ACHIEVEMENTS BY STUDENT AND TYPE
    @GetMapping("/student/{studentId}/type/{achievementType}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<StudentAchievementResponse>>
    getAchievementsByStudentAndType(
            @PathVariable Long studentId,
            @PathVariable String achievementType) {

        return ResponseEntity.ok(
                achievementService.getAchievementsByStudentAndType(
                        studentId,
                        achievementType
                )
        );
    }

    // UPDATE ACHIEVEMENT
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<StudentAchievementResponse> updateAchievement(
            @PathVariable Long id,
            @Valid @RequestBody StudentAchievementRequest request) {

        return ResponseEntity.ok(
                achievementService.updateAchievement(id, request)
        );
    }

    // DELETE ACHIEVEMENT
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteAchievement(
            @PathVariable Long id) {

        achievementService.deleteAchievement(id);

        return ResponseEntity.noContent().build();
    }
}
