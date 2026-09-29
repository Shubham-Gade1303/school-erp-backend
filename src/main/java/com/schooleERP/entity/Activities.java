package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String activityName;

    @Column(nullable = false, length = 100)
    private String activityType;

    @Column(length = 1000)
    private String description;

    @Column(length = 200)
    private String venue;

    @Column(nullable = false)
    private boolean active;
}