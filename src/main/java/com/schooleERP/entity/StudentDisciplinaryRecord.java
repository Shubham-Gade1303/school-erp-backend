package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_disciplinary_records")
@Getter
@Setter
@NoArgsConstructor
public class StudentDisciplinaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private LocalDate incidentDate;

    @Column(nullable = false, length = 100)
    private String incidentType;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 500)
    private String actionTaken;

    @Column(length = 1000)
    private String remarks;

    @Column(nullable = false)
    private boolean resolved;

    @Column(nullable = false)
    private LocalDate recordedDate;

    @PrePersist
    protected void onCreate() {

        if (recordedDate == null) {
            recordedDate = LocalDate.now();
        }
    }
}

