package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_achievements")
@Getter
@Setter
@NoArgsConstructor
public class StudentAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDate achievementDate;

    @Column(nullable = false, length = 100)
    private String achievementType;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 50)
    private String level;

    @Column(length = 50)
    private String position;

    @Column(length = 200)
    private String awardedBy;

    @Column(length = 500)
    private String certificateUrl;

    @Column(length = 1000)
    private String remarks;

    @Column(nullable = false)
    private LocalDate recordedDate;

    @PrePersist
    protected void onCreate() {
        if (recordedDate == null) {
            recordedDate = LocalDate.now();
        }
    }
}
