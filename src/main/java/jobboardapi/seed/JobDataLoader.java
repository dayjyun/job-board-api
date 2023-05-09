package jobboardapi.seed;

import jobboardapi.models.Job;
import jobboardapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class JobDataLoader implements CommandLineRunner {

    @Autowired
    JobRepository jobRepository;

    @Override
    public void run(String... args) throws Exception {
        loadJobData();
    }

    private void loadJobData() {
        if (jobRepository.count() == 0) {
            Job job1 = new Job(1L, "DeShe's Job", "Job 1 description", "Michigan", 100000.00, false, Timestamp.valueOf(LocalDateTime.of(2023, 5, 1, 12, 0, 0)));
            Job job2 = new Job(2L, "Kevin's Job", "Job 2 description", "Illinois", 100000.00, false, Timestamp.valueOf(LocalDateTime.of(2023, 6, 1, 12, 0, 0)));
            Job job3 = new Job(3L, "Kim's Job", "Job 3 description", "Illinois", 100000.00, false, Timestamp.valueOf(LocalDateTime.of(2023, 7, 1, 12, 0, 0)));
            jobRepository.save(job1);
            jobRepository.save(job2);
            jobRepository.save(job3);
        }
    }
}
