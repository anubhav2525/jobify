package com.naukari.server.repository.candidate;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.candidate.CandidateDocument;
import com.naukari.server.model.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateDocumentRepo extends JpaRepository<CandidateDocument, Long> {

    // Get all documents for a candidate
    List<CandidateDocument> findByCandidate(Candidate candidate);

    // Get all primary documents
    List<CandidateDocument> findByCandidateAndIsPrimaryTrue(Candidate candidate);

    // Get documents by file type
    List<CandidateDocument> findByCandidateAndDocumentType(Candidate candidate, FileType fileType);

    // Optional: Find a document by candidate and file name
    Optional<CandidateDocument> findByCandidateAndFileName(Candidate candidate, String fileName);
}
