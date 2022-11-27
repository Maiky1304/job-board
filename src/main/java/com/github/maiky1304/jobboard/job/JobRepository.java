package com.github.maiky1304.jobboard.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Job findByTitleContainsIgnoreCase(String title);

}
