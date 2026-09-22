package com.schooleERP.repository;

import com.schooleERP.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {

    List<Announcement> findByAudience(String audience);

    List<Announcement> findByActiveTrue();

    List<Announcement> findByAudienceAndActiveTrue(String audience);

    List<Announcement> findByTitleContainingIgnoreCase(String title);
}
