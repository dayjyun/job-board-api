package jobboardapi.repository;

import jobboardapi.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRepository extends JpaRepository<Job, Long> {
}
