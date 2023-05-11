package jobboardapi.service;

import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.JobRepository;
import jobboardapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private JobRepository jobRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * getAllJobs retrieves the list of all jobs from the job repository
     * @return a list of jobs
     */
    public List<Job> getAllJobListings() {
        return jobRepository.findAll();
    }

    /**
     * getJobListingById retrieves the job by the job id, if the job id exists
     * If the job id does not exist, a NotFoundException is thrown
     * @param jobId is what we're searching by
     * @return the optional of the job
     */
    public Optional<Job> getJobListingById(Long jobId) {
        Optional<Job> job = jobRepository.findById(jobId);
        if(job.isPresent()) {
            return job;
        } else {
            throw new NotFoundException("Job listing not found");
        }
    }

    /**
     * updateJobListing updates the job by searching for a job's ID
     * If the job id is does not exist, a NotFoundException is thrown
     * @param jobId is our target job ID
     * @param jobBody updated job details
     * @return the updated Job object
     */
    public Job updateJobListing(Long jobId, Job jobBody){
        Optional<Job> job = jobRepository.findById(jobId);
        if(job.isPresent()) {
            Job updatedJobListing = jobRepository.findById(jobId).get();
            updatedJobListing.setTitle(jobBody.getTitle());
            updatedJobListing.setDescription(jobBody.getDescription());
            updatedJobListing.setLocation(jobBody.getLocation());
            updatedJobListing.setSalary(jobBody.getSalary());
            updatedJobListing.setApplied(jobBody.isApplied());
            return jobRepository.save(updatedJobListing);
        } else {
            throw new NotFoundException("Job listing not found");
        }
    }

    /**
     * deleteJobListing checks if a job id is present in the job database
     * If the job id does not exist, a NotFoundException is thrown
     * If the job id exists, the job is deleted from the database, and the deleted job's details are returned.
     * @param jobId is the id for the job the user wants to delete
     * @return the deleted job's details
     */
    public Job deleteJobListing(Long jobId) {
        Optional<Job> jobListing = jobRepository.findById(jobId);
        if(jobListing.isPresent()) {
            jobRepository.deleteById(jobId);
            return jobListing.get();
        } else {
            throw new NotFoundException("Job listing not found");
        }
    }

    public List<User> getListOfApplicants(Long jobId) {
        Optional<Job> jobListing = jobRepository.findById(jobId);
        if(jobListing.isPresent()) {
            // Update jobRepository to search for list of applicants
            // Should no longer search through userRepository.
            List<User> userList = userRepository.findAll();
            if(userList.size() > 0) {
                return userList;
            } else {
                // Return a message instead of throwing an exception
                throw new NotFoundException("No applicants found");
            }
        } else {
            throw new NotFoundException("Job listing not found");
        }
    }
}
