
package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_admissions")
@Getter
@Setter
@NoArgsConstructor
public class StudentAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "student_id",
            nullable = false,
            unique = true
    )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "academic_year_id",
            nullable = false
    )
    private AcademicYear academicYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "standard_id",
            nullable = false
    )
    private Standard standard;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "class_section_id",
            nullable = false
    )
    private ClassSection classSection;

    @Column(nullable = false)
    private LocalDate admissionDate;

    @Column(nullable = false, length = 20)
    private String admissionStatus;

    @Column(length = 500)
    private String remarks;
}

