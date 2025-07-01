package com.naukari.server.repository.candidate;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.candidate.CandidateCertification;
import com.naukari.server.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateCertificationRepo extends JpaRepository<CandidateCertification, Long> {
    // Get all certifications by candidate
    List<CandidateCertification> findByCandidate(Candidate candidate);

    // Filter by verification status
    List<CandidateCertification> findByCandidateAndVerificationStatus(Candidate candidate, VerificationStatus status);

    // All pending verifications
    List<CandidateCertification> findAllByVerificationStatus(VerificationStatus status);

    // Search by certification name
    List<CandidateCertification> findByCandidateAndCertificationNameContainingIgnoreCase(Candidate candidate, String keyword);
}
