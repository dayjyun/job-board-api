package jobboardapi.repository;

import jobboardapi.models.Job;
import jobboardapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
//   List<User> findAllByApplicantsList(List<User> applicantsList)findAllByApplicantsList(List<User> applicantsList);
}
