package com.github.maiky1304.jobboard.job;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping("{id}")
    public Job readJob(@PathVariable Long id) {
        return jobService.readJob(id);
    }

    @GetMapping
    public List<Job> readJobs() {
        return jobService.readJobs();
    }

    @PutMapping("{id}")
    @Secured({ "ADMIN" })
    public Job updateJob(@PathVariable Long id, @RequestBody Job job, Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("{id}")
    @Secured({ "ADMIN" })
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }

}
