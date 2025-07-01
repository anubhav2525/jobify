package com.naukari.server.repository.company;

import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.entity.company.CompanyRecruiter;
import com.naukari.server.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRecruiterRepo extends JpaRepository<CompanyRecruiter, Long> {
    List<CompanyRecruiter> findByCompany(Company company);

    List<CompanyRecruiter> findByCompanyAndIsActiveTrue(Company company);

    boolean existsByCompanyAndUser(Company company, User user);

    Optional<CompanyRecruiter> findByCompanyAndUser(Company company, User user);

    // Get all recruiters who joined (useful for onboarding flow)
    List<CompanyRecruiter> findByJoinedAtIsNotNull();

    // Recruiters who haven’t accepted invites yet
    List<CompanyRecruiter> findByJoinedAtIsNull();

    // Recruiters created by a specific admin
    List<CompanyRecruiter> findByCreatedBy(Long createdBy);

    // Search by designation or description keyword
    List<CompanyRecruiter> findByDesignationContainingIgnoreCase(String designation);
    List<CompanyRecruiter> findByDescriptionContainingIgnoreCase(String keyword);

    // Find all recruiters added within a date range
    List<CompanyRecruiter> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    // Count recruiters per company (can be used with @Query if aggregation is needed)
    long countByCompany(Company company);

    // Count only active recruiters for a company
    long countByCompanyAndIsActiveTrue(Company company);

    // Search recruiter by email (if User entity has `email`, you'd need a join or projection for this)
    // You'd need JPQL or native query if accessing user.email directly

    // Example: recruiters not active for dashboard alerts
    List<CompanyRecruiter> findByCompanyAndIsActiveFalse(Company company);
}
