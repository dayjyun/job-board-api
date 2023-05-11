package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // User Story: I want to see a list of all jobs available
    // http://localhost:8080/api/jobs
    @GetMapping(path = "")
    public List<Job> getAllJobListings() {
        return jobService.getAllJobListings();
    }

    // User Story: I want to see a specific job listing
    // http://localhost:8080/api/jobs/{jobId}
    @GetMapping(path = "/{jobId}")
    public Optional<Job> getJobListingById(@PathVariable Long jobId) {
        return jobService.getJobListingById(jobId);
    }
}
