package com.naukari.server.model.entity.jobs;

import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id", nullable = false)
    private JobCategory jobCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "posted_by")
    private Long postedBy;

    @NotBlank(message = "Job title cannot be blank")
    @Size(max = 255, message = "Job title cannot exceed 255 characters")
    @Column(nullable = false, length = 255)
    private String title;

    @Size(max = 255, message = "Slug cannot exceed 255 characters")
    private String slug;

    @NotBlank(message = "Job description cannot be blank")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String requirements;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @NotNull(message = "Job type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false, length = 50)
    private JobType jobType = JobType.FULL_TIME;

    @NotNull(message = "Work location cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "work_location_type", nullable = false, length = 50)
    private WorkLocationType workLocationType = WorkLocationType.ONSITE;

    @NotNull(message = "Experience level cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level", nullable = false, length = 50)
    private ExperienceLevel experienceLevel;

    @Min(value = 0, message = "Minimum experience years cannot be negative")
    @Column(name = "min_experience_years", columnDefinition = "integer default 0")
    private Integer minExperienceYears = 0;

    @Column(name = "max_experience_years")
    private Integer maxExperienceYears;

    @DecimalMin(value = "0.0", message = "Minimum salary cannot be negative")
    @Column(name = "min_salary", precision = 12, scale = 2)
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary cannot be negative")
    @Column(name = "max_salary", precision = 12, scale = 2)
    private BigDecimal maxSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", length = 3, columnDefinition = "varchar(3) default 'INR'")
    private CurrencyType currencyType = CurrencyType.INR;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type", length = 20)
    private SalaryType salaryType = SalaryType.MONTHLY;

    @Size(max = 100, message = "City name cannot exceed 100 characters")
    @Column(name = "location_city", length = 100)
    private String locationCity;

    @Size(max = 100, message = "State name cannot exceed 100 characters")
    @Column(name = "location_state", length = 100)
    private String locationState;

    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    @Column(name = "location_country", length = 100)
    private String locationCountry;

    @Column(name = "is_salary_negotiable", columnDefinition = "boolean default false")
    private Boolean isSalaryNegotiable = false;

    @Column(name = "application_deadline", nullable = false)
    private LocalDateTime applicationDeadline;

    @Min(value = 1, message = "Total positions must be at least 1")
    @Column(name = "total_positions", columnDefinition = "integer default 1")
    private Integer totalPositions = 1;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "varchar(20) default 'draft'", name = "job_status")
    private JobStatus status = JobStatus.DRAFT;

    @Column(name = "is_featured", columnDefinition = "boolean default false")
    private Boolean isFeatured = false;

    @Column(name = "is_urgent", columnDefinition = "boolean default false")
    private Boolean isUrgent = false;

    @Min(value = 0, message = "View count cannot be negative")
    @Column(name = "view_count", columnDefinition = "integer default 0")
    private Integer viewCount = 0;

    @Min(value = 0, message = "Application count cannot be negative")
    @Column(name = "application_count", columnDefinition = "integer default 0")
    private Integer applicationCount = 0;

    @Column(name = "ai_match_score", precision = 5, scale = 2)
    private BigDecimal aiMatchScore;

    @Size(max = 255, message = "SEO title cannot exceed 255 characters")
    @Column(name = "seo_title", length = 255)
    private String seoTitle;

    @Column(name = "seo_description", columnDefinition = "TEXT")
    private String seoDescription;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "job_skills", nullable = false)
    private List<String> jobSkills = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
