package com.schooleERP.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_performance_summary" , uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "exam_id"}))
@Getter
@Setter
@NoArgsConstructor
public class StudentPerformanceSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "exam_id",nullable = false)
    private Exam exam;

    @Column(nullable = false)
    private Double totalMarks;

    @Column(nullable = false)
    private Double marksObtained;

    @Column(nullable = false)
    private Double percentage;


    @Column(nullable = false, length = 5)
    private String grade;

    @Column(nullable = false, length = 20)
    private String resultStatus;






}
