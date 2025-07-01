package com.naukari.server.repository.job;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.jobs.Job;
import com.naukari.server.model.entity.jobs.JobApplication;
import com.naukari.server.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplication, Long> {

    // Basic lookups
    List<JobApplication> findByJob(Job job);

    List<JobApplication> findByCandidate(Candidate candidate);

    Optional<JobApplication> findByJobAndCandidate(Job job, Candidate candidate);

    // Filter by status
    List<JobApplication> findByJobAndApplicationStatus(Job job, ApplicationStatus status);

    List<JobApplication> findByCandidateAndApplicationStatus(Candidate candidate, ApplicationStatus status);

    long countByJobAndApplicationStatus(Job job, ApplicationStatus status);

    // Analytics
    long countByJob(Job job);

    long countByApplicationStatus(ApplicationStatus status);

    // Sorting / Ratings
    List<JobApplication> findByJobOrderByAiMatchScoreDesc(Job job);

    List<JobApplication> findByJobOrderByRecruiterRatingDesc(Job job);

    List<JobApplication> findByJobAndRecruiterRatingGreaterThanEqual(Job job, int minRating);

    // Date-based filters
    List<JobApplication> findByCreatedAtAfter(LocalDateTime after);

    List<JobApplication> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Source filters (direct, referral, etc.)
    List<JobApplication> findByApplicationSource(String applicationSource);

    // Salary expectation filters
    List<JobApplication> findBySalaryExpectationBetween(BigDecimal min, BigDecimal max);
}
