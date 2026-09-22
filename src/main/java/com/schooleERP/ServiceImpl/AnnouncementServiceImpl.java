package com.schooleERP.ServiceImpl;

import com.schooleERP.dto.AnnouncementRequest;
import com.schooleERP.dto.AnnouncementResponse;
import com.schooleERP.entity.Announcement;
import com.schooleERP.repository.AnnouncementRepo;
import com.schooleERP.service.AnnouncementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepo announcementRepo;

    public AnnouncementServiceImpl(
            AnnouncementRepo announcementRepo
    ) {
        this.announcementRepo = announcementRepo;
    }

    // ==========================================
    // CREATE ANNOUNCEMENT
    // ==========================================

    @Override
    public AnnouncementResponse createAnnouncement(
            AnnouncementRequest request
    ) {

        Announcement announcement = new Announcement();

        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setAudience(request.getAudience());
        announcement.setPublishedAt(request.getPublishedAt());
        announcement.setActive(request.isActive());

        Announcement savedAnnouncement =
                announcementRepo.save(announcement);

        return mapToResponse(savedAnnouncement);
    }

    // ==========================================
    // GET ALL ANNOUNCEMENTS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // GET ANNOUNCEMENT BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public AnnouncementResponse getAnnouncementById(
            Long id
    ) {

        Announcement announcement =
                announcementRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        return mapToResponse(announcement);
    }

    // ==========================================
    // GET BY AUDIENCE
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getAnnouncementsByAudience(
            String audience
    ) {

        return announcementRepo.findByAudience(audience)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // GET ACTIVE ANNOUNCEMENTS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse> getActiveAnnouncements() {

        return announcementRepo.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // GET ACTIVE BY AUDIENCE
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse>
    getActiveAnnouncementsByAudience(
            String audience
    ) {

        return announcementRepo
                .findByAudienceAndActiveTrue(audience)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // SEARCH BY TITLE
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<AnnouncementResponse>
    searchAnnouncementsByTitle(
            String title
    ) {

        return announcementRepo
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================================
    // UPDATE ANNOUNCEMENT
    // ==========================================

    @Override
    public AnnouncementResponse updateAnnouncement(
            Long id,
            AnnouncementRequest request
    ) {

        Announcement announcement =
                announcementRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setAudience(request.getAudience());
        announcement.setPublishedAt(request.getPublishedAt());
        announcement.setActive(request.isActive());

        Announcement updatedAnnouncement =
                announcementRepo.save(announcement);

        return mapToResponse(updatedAnnouncement);
    }

    // ==========================================
    // DELETE ANNOUNCEMENT
    // ==========================================

    @Override
    public void deleteAnnouncement(Long id) {

        Announcement announcement =
                announcementRepo.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Announcement not found with id: "
                                                + id
                                )
                        );

        announcementRepo.delete(announcement);
    }

    // ==========================================
    // ENTITY → RESPONSE
    // ==========================================

    private AnnouncementResponse mapToResponse(
            Announcement announcement
    ) {

        AnnouncementResponse response =
                new AnnouncementResponse();

        response.setId(announcement.getId());
        response.setTitle(announcement.getTitle());
        response.setContent(announcement.getContent());
        response.setAudience(announcement.getAudience());
        response.setPublishedAt(
                announcement.getPublishedAt()
        );
        response.setActive(announcement.isActive());
        response.setCreatedAt(
                announcement.getCreatedAt()
        );
        response.setUpdatedAt(
                announcement.getUpdatedAt()
        );

        return response;
    }
}
