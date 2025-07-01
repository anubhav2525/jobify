package com.naukari.server.repository.company;

import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.entity.company.CompanyDocument;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.FileType;
import com.naukari.server.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyDocumentRepo extends JpaRepository<CompanyDocument, Long> {

    // Get all documents for a company
    List<CompanyDocument> findByCompany(Company company);

    // Get documents by type
    List<CompanyDocument> findByCompanyAndDocumentType(Company company, FileType documentType);

    // Find all pending documents for admin
    List<CompanyDocument> findAllByVerificationStatus(VerificationStatus status);

    // Optional: Find by company + document name
    Optional<CompanyDocument> findByCompanyAndDocumentName(Company company, String documentName);

    // Get documents verified by a specific user
    List<CompanyDocument> findByVerifiedBy(User verifiedBy);
}
