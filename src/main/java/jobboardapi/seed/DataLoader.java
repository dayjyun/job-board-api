package jobboardapi.seed;

import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.repository.JobRepository;
import jobboardapi.repository.UserRepository;
import jobboardapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    JobRepository jobRepository;

    @Override
    public void run(String... args) throws Exception{
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0 && businessRepository.count() == 0 && jobRepository.count() == 0) {

            User user1 = new User(1L, "DeShe", "deshe@gmail.com", "pw", "resume1");
            User user2 = new User(2L, "Kevin", "kevin@gmail.com", "pw", "resume2");
            User user3 = new User(3L, "Kim", "kim@gmail.com", "pw", "resume3");
            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);

            Business business1 = new Business(1L, "DeShe's Business", "Michigan");
            Business business2 = new Business(2L, "Kevin's Business", "Illinois");
            Business business3 = new Business(3L, "Kim's Business", "Illinois");
            businessRepository.save(business1);
            businessRepository.save(business2);
            businessRepository.save(business3);

            Job job1 = new Job(1L, "DeShe's Job", "Job 1 description", "Michigan", 100000.00, false);
            Job job2 = new Job(2L, "Kevin's Job", "Job 2 description", "Illinois", 100000.00, false);
            Job job3 = new Job(3L, "Kim's Job", "Job 3 description", "Illinois", 100000.00, false);
            jobRepository.save(job1);
            jobRepository.save(job2);
            jobRepository.save(job3);
        }
    }
}
