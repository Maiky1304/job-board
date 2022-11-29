package com.github.maiky1304.jobboard.job;

import com.github.maiky1304.jobboard.job.exceptions.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class JobControllerAdvice {

    @ResponseBody
    @ExceptionHandler(JobNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String jobNotFoundHandler(JobNotFoundException e) {
        return e.getMessage();
    }

}
