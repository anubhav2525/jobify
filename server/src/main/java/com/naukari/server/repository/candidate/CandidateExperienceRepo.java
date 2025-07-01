package com.naukari.server.repository.candidate;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.candidate.CandidateEducation;
import com.naukari.server.model.entity.candidate.CandidateExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateExperienceRepo extends JpaRepository<CandidateExperience, Long> {
    // Get all education entries for a candidate
    List<CandidateEducation> findByCandidate(Candidate candidate);
}
