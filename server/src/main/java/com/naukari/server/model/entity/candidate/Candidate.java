package com.naukari.server.model.entity.candidate;

import com.naukari.server.model.dto.SocialLink;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.*;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import lombok.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User userId;

    @Column(name = "headline", nullable = false, length = 255)
    @Size(max = 255)
    private String headline;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "current_job_title", length = 255)
    private String currentJobTitle;

    @Column(name = "current_company", length = 255)
    private String currentCompany;

    @Column(name = "total_experience_years", precision = 4, scale = 2)
    private BigDecimal totalExperienceYears;

    @Column(name = "current_salary", precision = 12, scale = 2)
    private BigDecimal currentSalary;

    @Column(name = "expected_salary", precision = 12, scale = 2)
    private BigDecimal expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_currency", length = 3)
    private CurrencyType currencyType = CurrencyType.INR;

    @Column(name = "notice_period_days")
    private Integer noticePeriodDays;

    @ElementCollection
    @CollectionTable(name = "candidate_preferred_locations", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "location")
    private List<String> preferredLocations = new ArrayList<>();

    @Column(name = "willing_to_relocate")
    private Boolean willingToRelocate = false;

    @Embedded
    private SocialLink social;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 20, nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality", length = 100, nullable = false)
    private Nationality nationality = Nationality.INDIAN;

    @Column(name = "is_profile_public")
    private Boolean isProfilePublic = true;

    @Column(name = "is_open_to_work")
    private Boolean isOpenToWork = true;

    @Column(name = "profile_completion_score", precision = 5, scale = 2)
    private BigDecimal profileCompletionScore = BigDecimal.ZERO;

    @Column(name = "ai_profile_score", precision = 5, scale = 2)
    private BigDecimal aiProfileScore;

    @Column(name = "last_activity_at")
    private LocalDateTime lastActivityAt;

    @ElementCollection
    @CollectionTable(name = "candidate_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CandidateEducation> education = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CandidateCertification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CandidateDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CandidateExperience> experiences = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
