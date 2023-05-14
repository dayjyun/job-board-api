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
            User deshe = new User(1L, "DeShe", "deshe@gmail.com", "pw", "resume1");
            User kevin = new User(2L, "Kevin", "kevin@gmail.com", "pw", "resume2");
            User kim = new User(3L, "Kim", "kim@gmail.com", "pw", "resume3");
            userService.createUser(deshe);
            userService.createUser(kevin);
            userService.createUser(kim);

            // create businesses
            Business dBusiness = new Business(1L, "DeShe's Business", "Michigan");
//            Business dBusiness2 = new Business(4L, "D's 2nd Bus", "Mich");
            Business kevBusiness = new Business(2L, "Kevin's Business", "Illinois");
            Business kimBusiness = new Business(3L, "Kim's Business", "Illinois");

            // for business, show the user that created it
            // assign an owner (user) for each business
            dBusiness.setUser(deshe);
//            dBusiness2.setUser(deshe);
            kevBusiness.setUser(kevin);
            kimBusiness.setUser(kim);

            // for user, show a list of businesses they own
            // assign a business list for each owner (user)
//            ArrayList<Business> businessListDeShe = new ArrayList<>();
//            ArrayList<Business> businessListKevin = new ArrayList<>();
//            ArrayList<Business> businessListKim = new ArrayList<>();
//            businessListDeShe.add(dBusiness);
//            businessListKevin.add(kevBusiness);
//            businessListKim.add(kimBusiness);
//            deshe.setBusinessList(businessListDeShe);
//            kevin.setBusinessList(businessListKevin);
//            kim.setBusinessList(businessListKim);



            businessRepository.save(dBusiness);
//            businessRepository.save(dBusiness2);
            businessRepository.save(kevBusiness);
            businessRepository.save(kimBusiness);

            // duplicate job list, json ignore?

            // create jobs
            Job job1 = new Job(1L, "Job for Deshe's business", "Job 1 description", "Michigan", 100000.00);
            Job job2 = new Job(2L, "Job for Kevin's business", "Job 2 description", "Illinois", 100000.00);
            Job job3 = new Job(3L, "Job for Kim's business", "Job 3 description", "Illinois", 100000.00);

            // for job, show the business it belongs to
            // assign a business where each job comes from
            job1.setBusiness(dBusiness);
            job2.setBusiness(kevBusiness);
            job3.setBusiness(kimBusiness);

            // for job, show the user that created it
            // assign a creator (user) for each job
            job1.setUser(deshe);
            job2.setUser(kevin);
            job3.setUser(kim);


            // for user, show a list of jobs they created
            // assign a list of jobs created to their creator (user)
            ArrayList<Job> jobListDeShe = new ArrayList<>();
            ArrayList<Job> jobListKevin = new ArrayList<>();
            ArrayList<Job> jobListKim = new ArrayList<>();
            jobListDeShe.add(job1);
            jobListKevin.add(job2);
            jobListKim.add(job3);
            deshe.setListOfJobsAppliedTo(jobListDeShe);
            deshe.setListOfJobsAppliedTo(jobListKevin);
            deshe.setListOfJobsAppliedTo(jobListKim);

            // // // // // getListOfJobsAppliedTo.add????
            deshe.getListOfJobsAppliedTo().add(job1);
            deshe.getListOfJobsAppliedTo().add(job2);
            deshe.getListOfJobsAppliedTo().add(job3);

            // doesn't work
            // for user, show the job id that they applied for
            // assign an applicant to each job
            deshe.setJob(job2); // DeShe applied for Kevin's Job
            kevin.setJob(job3); // Kevin applied for Kim's Job
            kim.setJob(job1); // Kim applied for DeShe's Job

            // for business, show a list of jobs it has
            // assign a list of jobs to each business
            dBusiness.setListOfJobsAvailable(jobListDeShe);
            kevBusiness.setListOfJobsAvailable(jobListKevin);
            kimBusiness.setListOfJobsAvailable(jobListKim);

            // for job, show a list of applicants
            // assign a list of applicants to the job they applied for
            ArrayList<User> applicantsJob1 = new ArrayList<>();
            ArrayList<User> applicantsJob2 = new ArrayList<>();
            ArrayList<User> applicantsJob3 = new ArrayList<>();
            applicantsJob1.add(kim); // Kim applied for DeShe's Job
            applicantsJob2.add(deshe); // DeShe applied for Kevin's Job
            applicantsJob3.add(kevin); // Kevin applied for Kim's Job
            job1.setApplicantsList(applicantsJob1);
            job2.setApplicantsList(applicantsJob2);
            job3.setApplicantsList(applicantsJob3);

            jobRepository.save(job1);
            jobRepository.save(job2);
            jobRepository.save(job3);
        }
    }
}