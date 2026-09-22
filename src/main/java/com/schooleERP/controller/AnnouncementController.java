package com.schooleERP.controller;

import com.schooleERP.dto.AnnouncementRequest;
import com.schooleERP.dto.AnnouncementResponse;
import com.schooleERP.service.AnnouncementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(
            AnnouncementService announcementService
    ) {
        this.announcementService = announcementService;
    }

    // CREATE ANNOUNCEMENT

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<AnnouncementResponse> createAnnouncement(
            @Valid @RequestBody AnnouncementRequest request
    ) {

        AnnouncementResponse response = announcementService.createAnnouncement(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL ANNOUNCEMENTS

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AnnouncementResponse>>
    getAllAnnouncements() {

        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    // GET ANNOUNCEMENT BY ID

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<AnnouncementResponse>
    getAnnouncementById(@PathVariable Long id
    ) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }

    // GET ANNOUNCEMENTS BY AUDIENCE

    @GetMapping("/audience/{audience}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AnnouncementResponse>>
    getAnnouncementsByAudience(
            @PathVariable String audience
    ) {

        return ResponseEntity.ok(announcementService.getAnnouncementsByAudience(audience));
    }

    // GET ACTIVE ANNOUNCEMENTS

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AnnouncementResponse>>
    getActiveAnnouncements() {

        return ResponseEntity.ok(announcementService.getActiveAnnouncements()
        );
    }

    // GET ACTIVE ANNOUNCEMENTS BY AUDIENCE

    @GetMapping("/audience/{audience}/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AnnouncementResponse>>
    getActiveAnnouncementsByAudience(
            @PathVariable String audience
    ) {
        return ResponseEntity.ok(announcementService.getActiveAnnouncementsByAudience(audience)
        );
    }

    // SEARCH ANNOUNCEMENTS BY TITLE

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ResponseEntity<List<AnnouncementResponse>>
    searchAnnouncementsByTitle(
            @RequestParam String title
    ) {
        return ResponseEntity.ok(
                announcementService.searchAnnouncementsByTitle(title)
        );
    }
    // UPDATE ANNOUNCEMENT

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<AnnouncementResponse>
    updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequest request
    ) {
        return ResponseEntity.ok(
                announcementService.updateAnnouncement(id, request)
        );
    }

    // DELETE ANNOUNCEMENT

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable Long id
    ) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
