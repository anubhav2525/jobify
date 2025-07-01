package com.naukari.server.repository.job;

import com.naukari.server.model.entity.jobs.JobAppQuesAns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAppQuesAnsRepo extends JpaRepository<JobAppQuesAns, Long> {
}
