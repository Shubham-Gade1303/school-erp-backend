package com.schooleERP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "parents_Data")
@Getter
@Setter

public class ParentsGuardians {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false,length = 150)
    private String fullName;

    @Column(nullable = false,length = 30)
    private String Relationship;

    @Column(length = 10, nullable = false)
    private String phoneNumber;

    @Column(length = 50)
    private String Email;


    @Column(nullable = false, length = 50)
    private String occupation;




}
