package com.naukari.server.repository.candidate;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.candidate.CandidateEducation;
import com.naukari.server.model.enums.EducationDegreeType;
import com.naukari.server.model.enums.StudyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateEducationRepo extends JpaRepository<CandidateEducation, Long> {
    List<CandidateEducation> findByCandidate(Candidate candidate);

    List<CandidateEducation> findByCandidateAndDegreeType(Candidate candidate, EducationDegreeType degreeType);

    List<CandidateEducation> findByCandidateAndStudyMode(Candidate candidate, StudyMode studyMode);
}
