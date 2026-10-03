package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_promotion_history")
@Getter
@Setter
@NoArgsConstructor
public class StudentPromotionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_academic_year_id", nullable = false)
    private AcademicYear fromAcademicYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_standard_id", nullable = false)
    private Standard fromStandard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_class_section_id", nullable = false)
    private ClassSection fromClassSection;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_academic_year_id", nullable = false)
    private AcademicYear toAcademicYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_standard_id", nullable = false)
    private Standard toStandard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_class_section_id", nullable = false)
    private ClassSection toClassSection;

    @Column(nullable = false)
    private LocalDate promotionDate;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 1000)
    private String remarks;

    @PrePersist
    protected void onCreate() {
        if (promotionDate == null) {
            promotionDate = LocalDate.now();
        }
    }
}
