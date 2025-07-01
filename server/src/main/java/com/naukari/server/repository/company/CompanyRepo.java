package com.naukari.server.repository.company;

import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.CompanySize;
import com.naukari.server.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

    // Get company by owner (user)
    Optional<Company> findByUser(User user);

    // Get company by slug (used in SEO-friendly URLs)
    Optional<Company> findByCompanySlug(String companySlug);

    // Check if company slug already exists
    boolean existsByCompanySlug(String companySlug);

    // Filter by active companies
    List<Company> findAllByIsActiveTrue();

    // Filter by industry
    List<Company> findAllByIndustryIgnoreCase(String industry);

    // Filter by company size
    List<Company> findAllByCompanySize(CompanySize size);

    // Filter by verification status
    List<Company> findAllByVerificationStatus(VerificationStatus status);

    // Search by name
    List<Company> findByCompanyNameContainingIgnoreCase(String keyword);

    // Optional: Search by legal name
    List<Company> findByLegalNameContainingIgnoreCase(String keyword);

    // Find by taxId or registrationNumber (for duplicate prevention)
    Optional<Company> findByTaxId(String taxId);
    Optional<Company> findByRegistrationNumber(String registrationNumber);

    // Search by website domain (for potential integrations)
    Optional<Company> findByWebsiteUrlContainingIgnoreCase(String domain);

    // Multiple criteria
    List<Company> findAllByIndustryIgnoreCaseAndCompanySize(String industry, CompanySize size);
    List<Company> findAllByIsActiveTrueAndVerificationStatus(VerificationStatus status);

    // Partial match in both legal and display names
    List<Company> findByCompanyNameContainingIgnoreCaseOrLegalNameContainingIgnoreCase(String name1, String name2);

    // Find all companies created after a certain date (e.g., for recent onboarding)
    List<Company> findAllByCreatedAtAfter(LocalDateTime after);

    // Find all companies updated within a time range (e.g., for audit)
    List<Company> findAllByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);

    // Count by verification status (for dashboard metrics)
    long countByVerificationStatus(VerificationStatus status);

    // Count total companies in a specific industry
    long countByIndustryIgnoreCase(String industry);

    // Find all companies with no documents (could be flagged for follow-up)
    @Query("SELECT c FROM Company c WHERE c.documents IS EMPTY")
    List<Company> findAllWithoutDocuments();
}
