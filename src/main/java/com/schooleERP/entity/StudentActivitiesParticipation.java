package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_activities_participation")
@Getter
@Setter
@NoArgsConstructor
public class StudentActivitiesParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activities activity;

    @Column(nullable = false)
    private LocalDate participationDate;

    @Column(length = 100)
    private String role;

    @Column(length = 100)
    private String result;

    @Column(length = 50)
    private String position;

    @Column(length = 1000)
    private String remarks;
}

