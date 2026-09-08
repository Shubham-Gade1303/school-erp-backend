package com.schooleERP.repository;

import com.schooleERP.entity.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassSectionRepo
        extends JpaRepository<ClassSection, Long> {

    @Query("""
            SELECT cs
            FROM ClassSection cs
            JOIN FETCH cs.academicYear
            JOIN FETCH cs.standard
            """)
    List<ClassSection> findAllWithDetails();

    @Query("""
            SELECT cs
            FROM ClassSection cs
            JOIN FETCH cs.academicYear
            JOIN FETCH cs.standard
            WHERE cs.id = :id
            """)
    Optional<ClassSection> findByIdWithDetails(
            @Param("id") Long id
    );

    @Query("""
            SELECT cs
            FROM ClassSection cs
            JOIN FETCH cs.academicYear
            JOIN FETCH cs.standard
            WHERE cs.academicYear.id = :academicYearId
            """)
    List<ClassSection> findByAcademicYearIdWithDetails(
            @Param("academicYearId") Long academicYearId
    );

    @Query("""
            SELECT cs
            FROM ClassSection cs
            JOIN FETCH cs.academicYear
            JOIN FETCH cs.standard
            WHERE cs.standard.id = :standardId
            """)
    List<ClassSection> findByStandardIdWithDetails(
            @Param("standardId") Long standardId
    );

    @Query("""
            SELECT cs
            FROM ClassSection cs
            JOIN FETCH cs.academicYear
            JOIN FETCH cs.standard
            WHERE cs.academicYear.id = :academicYearId
            AND cs.standard.id = :standardId
            """)
    List<ClassSection> findByAcademicYearIdAndStandardIdWithDetails(
            @Param("academicYearId") Long academicYearId,
            @Param("standardId") Long standardId
    );

    Optional<ClassSection>
    findByAcademicYearIdAndStandardIdAndSectionName(
            Long academicYearId,
            Long standardId,
            String sectionName
    );

    boolean existsByAcademicYearIdAndStandardIdAndSectionName(
            Long academicYearId,
            Long standardId,
            String sectionName
    );
}

