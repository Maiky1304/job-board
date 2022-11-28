package com.github.maiky1304.jobboard.job;

import com.github.maiky1304.jobboard.job.exceptions.JobNotFoundException;
import com.github.maiky1304.jobboard.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job readJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    public List<Job> readJobs() {
        return jobRepository.findAll();
    }

    public Job updateJob(Long id, Job job) {
        Job foundJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));

        if (job.getTitle() != null && !Objects.equals(job.getTitle(), foundJob.getTitle())) {
            foundJob.setTitle(job.getTitle());
        }
        if (job.getContent() != null && !Objects.equals(job.getContent(), foundJob.getContent())) {
            foundJob.setContent(job.getContent());
        }

        return jobRepository.save(foundJob);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public boolean isJobOwner(Long id, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User user)) {
            return false;
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
        return job.getAuthor().getUsername().equals(user.getUsername());
    }

}
