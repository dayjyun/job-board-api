package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping(path = "")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
