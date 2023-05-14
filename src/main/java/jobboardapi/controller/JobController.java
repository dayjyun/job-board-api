package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.service.JobService;
import jobboardapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.rmi.AlreadyBoundException;
import javax.validation.Valid;

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

    // User Story: I want to update my job listing
    // http://localhost:8080/api/jobs/{jobId}
    @PutMapping(path = "/{jobId}")
    public Job updateJobListing(@PathVariable Long jobId, @RequestBody @Valid Job jobBody) {
        return jobService.updateJobListing(jobId, jobBody);
    }

    // User Story: I want to delete a job listing from the job board
    // http://localhost:8080/api/jobs/{jobId}
    @DeleteMapping(path = "/{jobId}")
    public Job deleteJobListing(@PathVariable Long jobId) {
        return jobService.deleteJobListing(jobId);
    }

//    // User Story: I want to see a list of applicants that applied for my job listing
//    // http://localhost:8080/api/jobs/{jobId}/applicants
//    @GetMapping(path = "/{jobId}/applicants")
//    public List<User> getListOfApplicants(@PathVariable Long jobId) {
//        return jobService.getListOfApplicants(jobId);
//    }

    @PostMapping(path = "/{jobId}/applicants")
    public Optional<Job> applyForJobListing(@PathVariable Long jobId) {
        return jobService.applyForJobListing(jobId);
    }
}
