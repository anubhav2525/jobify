package com.naukari.server.repository.candidate;

import com.naukari.server.model.entity.candidate.Candidate;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.Gender;
import com.naukari.server.model.enums.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    // Find by user
    Optional<Candidate> findByUserId(User user);

    // Check existence by user
    boolean existsByUserId(User user);

    // Find all candidates by gender
    List<Candidate> findAllByGender(Gender gender);

    // Find all candidates by nationality
    List<Candidate> findAllByNationality(Nationality nationality);

    // Find candidates open to work
    List<Candidate> findAllByIsOpenToWorkTrue();

    // Find candidates by preferred location
    List<Candidate> findByPreferredLocationsContaining(String location);

    // Search candidates who are open to relocation
    List<Candidate> findByWillingToRelocateTrue();

    // Search public profiles
    List<Candidate> findAllByIsProfilePublicTrue();

    // Top candidates by profile completion score
    List<Candidate> findTop10ByOrderByProfileCompletionScoreDesc();

    // Top candidates by AI profile score
    List<Candidate> findTop10ByOrderByAiProfileScoreDesc();

    // Find candidates which have specific skill
    List<Candidate> findBySkillsContaining(String skill);
}