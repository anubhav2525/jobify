package com.naukari.server.repository.job;

import com.naukari.server.model.entity.jobs.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobCategoryRepo extends JpaRepository<JobCategory, Long> {

    // Standard finders
    Optional<JobCategory> findBySlug(String slug);

    Optional<JobCategory> findByNameIgnoreCase(String name);

    boolean existsBySlug(String slug);

    boolean existsByNameIgnoreCase(String name);

    // Filtering
    List<JobCategory> findByIsActiveTrue();

    List<JobCategory> findByIsActive(boolean isActive);

    // Search
    List<JobCategory> findByNameContainingIgnoreCase(String keyword);

    List<JobCategory> findByDescriptionContainingIgnoreCase(String keyword);
}
