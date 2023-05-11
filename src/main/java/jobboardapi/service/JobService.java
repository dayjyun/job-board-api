package jobboardapi.service;

import jobboardapi.models.Job;
import jobboardapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
