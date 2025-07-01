package com.naukari.server.repository.job;

import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.entity.jobs.Job;
import com.naukari.server.model.enums.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {

    // Standard Fetch Methods
    List<Job> findByCompany(Company company);

    Optional<Job> findBySlug(String slug);

    boolean existsBySlug(String slug);

    // Basic Filters
    List<Job> findByStatus(JobStatus status);

    List<Job> findByStatusAndCompany(JobStatus status, Company company);

    List<Job> findByWorkLocationType(WorkLocationType workLocationType);

    List<Job> findByJobType(JobType jobType);

    List<Job> findByExperienceLevel(ExperienceLevel level);

    // Salary-based Filtering
    List<Job> findByMinSalaryGreaterThanEqual(BigDecimal minSalary);

    List<Job> findByMaxSalaryLessThanEqual(BigDecimal maxSalary);

    List<Job> findByCurrencyType(CurrencyType currency);

    // Location-based Filters
    List<Job> findByLocationCountryIgnoreCase(String country);

    List<Job> findByLocationStateIgnoreCase(String state);

    List<Job> findByLocationCityIgnoreCase(String city);

    // Time-based Filters
    List<Job> findByCreatedAtAfter(LocalDateTime createdAfter);

    List<Job> findByPublishedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Job> findByApplicationDeadlineBefore(LocalDateTime before);

    // Advanced Status Filters
    List<Job> findByIsFeaturedTrue();

    List<Job> findByIsUrgentTrue();

    List<Job> findByStatusAndIsFeaturedTrue(JobStatus status);

    List<Job> findByStatusAndIsUrgentTrue(JobStatus status);

    // Keyword Search (basic, upgrade with @Query for full-text search)
    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByDescriptionContainingIgnoreCase(String description);

    // Analytics / Dashboard
    long countByCompany(Company company);

    long countByStatus(JobStatus status);

    long countByCompanyAndStatus(Company company, JobStatus status);

    // Optional: Filter published and not expired
    List<Job> findByStatusAndPublishedAtBeforeAndExpiresAtAfter(
            JobStatus status,
            LocalDateTime publishedBefore,
            LocalDateTime currentDate
    );

}
