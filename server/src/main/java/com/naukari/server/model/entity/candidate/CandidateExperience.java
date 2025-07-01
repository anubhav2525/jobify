package com.naukari.server.model.entity.candidate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class CandidateExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @NotBlank(message = "Company name is required")
    @Column(name = "company_name")
    private String companyName;

    @NotBlank(message = "Job title is required")
    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "exp_start")
    private LocalDate expStart;

    @Column(name = "exp_end")
    private LocalDate expEnd;

    @Column(name = "is_current_job")
    private boolean isCurrentJob;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "achievements", columnDefinition = "TEXT")
    private String achievements;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
