package com.naukari.server.model.entity.candidate;

import com.naukari.server.model.enums.EducationDegreeType;
import com.naukari.server.model.enums.StudyMode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "candidate_educations")
@NoArgsConstructor
@AllArgsConstructor
public class CandidateEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "institution_name", nullable = false)
    @Size(max = 255)
    private String institutionName;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree_type", nullable = false, length = 50)
    private EducationDegreeType degreeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_mode", length = 20)
    private StudyMode studyMode;

    @Column(name = "field_of_study")
    @Size(max = 255)
    private String fieldOfStudy;

    @Column(name = "grade_gpa")
    @Size(max = 20)
    private String gradeGpa;

    @Column(name = "education_start")
    private LocalDate educationStart;

    @Column(name = "education_end")
    private LocalDate educationEnd;

    @Column(name = "is_current_job")
    private Boolean isCurrentJob = false;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "activities", columnDefinition = "TEXT")
    private String activities;

    @Column(name = "order_index")
    private Integer orderIndex = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
