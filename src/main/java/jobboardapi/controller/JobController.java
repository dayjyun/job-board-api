package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/jobs")
public class JobController {

   @Autowired
   private JobService jobService;

   // Functionality: Returns all jobs in the job board
   // Path: http://localhost:8080/api/jobs
   @GetMapping(path = "")
   public List<Job> getAllJobListings() {
      return jobService.getAllJobListings();
   }

   // Functionality: Returns job listing details
   // Path: http://localhost:8080/api/jobs/{jobId}
   @GetMapping(path = "/{jobId}")
   public Optional<Job> getJobListingById(@PathVariable Long jobId) {
      return jobService.getJobListingById(jobId);
   }

   // Functionality: Update job listing details
   // Path: http://localhost:8080/api/jobs/{jobId}
   @PutMapping(path = "/{jobId}")
   public Job updateJobListing(@PathVariable Long jobId, @RequestBody @Valid Job jobBody) {
      return jobService.updateJobListing(jobId, jobBody);
   }

   // Functionality: Delete job listing
   // Path: http://localhost:8080/api/jobs/{jobId}
   @DeleteMapping(path = "/{jobId}")
   public Job deleteJobListing(@PathVariable Long jobId) {
      return jobService.deleteJobListing(jobId);
   }

   // Functionality: Returns a list of all applicants for the job
   // Path: http://localhost:8080/api/jobs/{jobId}/applicants
   @GetMapping(path = "/{jobId}/applicants")
   public List<User> getListOfApplicants(@PathVariable Long jobId) {
      return jobService.getListOfApplicants(jobId);
   }

   // Functionality: User applies for job
   // Path: http://localhost:8080/api/jobs/{jobId}/applicants
   @PostMapping(path = "/{jobId}/applicants")
   public Optional<Job> applyForJobListing(@PathVariable Long jobId) {
      return jobService.applyForJobListing(jobId);
   }
}
