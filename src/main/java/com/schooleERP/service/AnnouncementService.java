package com.schooleERP.service;

import com.schooleERP.dto.AnnouncementRequest;
import com.schooleERP.dto.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {

    AnnouncementResponse createAnnouncement(AnnouncementRequest request);

    List<AnnouncementResponse> getAllAnnouncements();

    AnnouncementResponse getAnnouncementById(Long id);

    List<AnnouncementResponse> getAnnouncementsByAudience(String audience);

    List<AnnouncementResponse> getActiveAnnouncements();

    List<AnnouncementResponse> getActiveAnnouncementsByAudience(String audience);

    List<AnnouncementResponse> searchAnnouncementsByTitle(String title);

    AnnouncementResponse updateAnnouncement(Long id, AnnouncementRequest request);

    void deleteAnnouncement(Long id);
}