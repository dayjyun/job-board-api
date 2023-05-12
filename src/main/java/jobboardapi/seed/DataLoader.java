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

import java.util.ArrayList;

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

            // create users
            User user1 = new User(1L, "DeShe", "deshe@gmail.com", "pw", "resume1");
            User user2 = new User(2L, "Kevin", "kevin@gmail.com", "pw", "resume2");
            User user3 = new User(3L, "Kim", "kim@gmail.com", "pw", "resume3");
            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);

            // create businesses
            Business business1 = new Business(1L, "DeShe's Business", "Michigan");
            Business business2 = new Business(2L, "Kevin's Business", "Illinois");
            Business business3 = new Business(3L, "Kim's Business", "Illinois");

            // for business, show the user that created it
            // assign an owner (user) for each business
            business1.setUser(user1);
            business2.setUser(user2);
            business3.setUser(user3);
            businessRepository.save(business1);
            businessRepository.save(business2);
            businessRepository.save(business3);

            // for user, show a list of businesses they own
            // assign a business list for each owner (user)
            ArrayList<Business> businessListDeShe = new ArrayList<>();
            ArrayList<Business> businessListKevin = new ArrayList<>();
            ArrayList<Business> businessListKim = new ArrayList<>();
            businessListDeShe.add(business1);
            businessListKevin.add(business2);
            businessListKim.add(business3);
            user1.setBusinessList(businessListDeShe);
            user1.setBusinessList(businessListKevin);
            user1.setBusinessList(businessListKim);

            // duplicate job list, json ignore?

            // create jobs
            Job job1 = new Job(1L, "DeShe's Job", "Job 1 description", "Michigan", 100000.00, false);
            Job job2 = new Job(2L, "Kevin's Job", "Job 2 description", "Illinois", 100000.00, false);
            Job job3 = new Job(3L, "Kim's Job", "Job 3 description", "Illinois", 100000.00, false);

            // for job, show the business it belongs to
            // assign a business where each job comes from
            job1.setBusiness(business1);
            job2.setBusiness(business2);
            job3.setBusiness(business3);

            // for job, show the user that created it
            // assign a creator (user) for each job
            job1.setUser(user1);
            job2.setUser(user2);
            job3.setUser(user3);

            jobRepository.save(job1);
            jobRepository.save(job2);
            jobRepository.save(job3);

            // for user, show a list of jobs they created
            // assign a list of jobs created to their creator (user)
            ArrayList<Job> jobListDeShe = new ArrayList<>();
            ArrayList<Job> jobListKevin = new ArrayList<>();
            ArrayList<Job> jobListKim = new ArrayList<>();
            jobListDeShe.add(job1);
            jobListKevin.add(job2);
            jobListKim.add(job3);
            user1.setJobList(jobListDeShe);
            user1.setJobList(jobListKevin);
            user1.setJobList(jobListKim);

            // doesn't work
            // for user, show the job id that they applied for
            // assign an applicant to each job
            user1.setJob(job2); // DeShe applied for Kevin's Job
            user2.setJob(job3); // Kevin applied for Kim's Job
            user3.setJob(job1); // Kim applied for DeShe's Job

            // for business, show a list of jobs it has
            // assign a list of jobs to each business
            business1.setJobList(jobListDeShe);
            business2.setJobList(jobListKevin);
            business3.setJobList(jobListKim);

            // for job, show a list of applicants
            // assign a list of applicants to the job they applied for
            ArrayList<User> applicantsJob1 = new ArrayList<>();
            ArrayList<User> applicantsJob2 = new ArrayList<>();
            ArrayList<User> applicantsJob3 = new ArrayList<>();
            applicantsJob1.add(user3); // Kim applied for DeShe's Job
            applicantsJob2.add(user1); // DeShe applied for Kevin's Job
            applicantsJob3.add(user2); // Kevin applied for Kim's Job
            job1.setApplicantsList(applicantsJob1);
            job2.setApplicantsList(applicantsJob2);
            job3.setApplicantsList(applicantsJob3);
        }
    }
}