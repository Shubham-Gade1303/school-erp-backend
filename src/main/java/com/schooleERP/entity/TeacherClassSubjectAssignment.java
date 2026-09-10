package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "teacher_class_subject_assignments",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "teacher_id",
                                "class_section_id",
                                "class_subject_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class TeacherClassSubjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "teacher_id",
            nullable = false
    )
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "class_section_id",
            nullable = false
    )
    private ClassSection classSection;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "class_subject_id",
            nullable = false
    )
    private ClassSubject classSubject;

    @Column(nullable = false)
    private boolean active = true;
}

