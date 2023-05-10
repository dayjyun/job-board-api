package jobboardapi.controller;

import jobboardapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
}
