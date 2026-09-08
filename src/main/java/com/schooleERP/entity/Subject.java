package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String subjectName;

    @Column(nullable = false, unique = true, length = 20)
    private String subjectCode;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active = true;
}
