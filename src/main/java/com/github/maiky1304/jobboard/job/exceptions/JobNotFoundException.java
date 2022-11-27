package com.github.maiky1304.jobboard.job.exceptions;

import org.springframework.web.client.RestClientException;

public class JobNotFoundException extends RestClientException {
    public JobNotFoundException(Long id) {
        super("Job with id " + id + " not found");
    }

}
