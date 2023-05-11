package jobboardapi.service;

import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Job;
import jobboardapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private JobRepository jobRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * getAllJobs retrieves the list of all jobs from the job repository
     * @return a list of jobs
     */
    public List<Job> getAllJobListings() {
        return jobRepository.findAll();
    }

    /**
     *
     */
    public Optional<Job> getJobListingById(Long jobId) {
        Optional<Job> job = jobRepository.findById(jobId);
        if(job.isPresent()) {
            return job;
        } else {
            throw new NotFoundException("Job not found");
        }
    }
}
