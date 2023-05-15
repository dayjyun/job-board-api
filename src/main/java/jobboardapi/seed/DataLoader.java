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
         Business kevBusiness = new Business(2L, "Kevin's Business", "Illinois");
         Business kimBusiness = new Business(3L, "Kim's Business", "Illinois");

         // create jobs
         Job dJob = new Job(1L, "DeShe's Job", "Job 1 description", "Michigan", 100000.00);
         Job kevJob = new Job(2L, "Kevin's Job", "Job 2 description", "Illinois", 100000.00);
         Job kimJob = new Job(3L, "Kim's Job", "Job 3 description", "Illinois", 100000.00);

         // for business, assign the owner (user) for each business
         dBusiness.setUser(deshe);
         kevBusiness.setUser(kevin);
         kimBusiness.setUser(kim);

         // for each job, assign a business where each job comes from
         dJob.setBusiness(dBusiness);
         kevJob.setBusiness(kevBusiness);
         kimJob.setBusiness(kimBusiness);

         // for job, show a list of applicants
         // assign a list of applicants to the job they applied for
         ArrayList<User> desheJobListOfApplicants = new ArrayList<>();
         ArrayList<User> kevinJobListOfApplicants = new ArrayList<>();
         ArrayList<User> kimJobListOfApplicants = new ArrayList<>();

         desheJobListOfApplicants.add(kim); // Kim applied for DeShe's job
         desheJobListOfApplicants.add(kevin); // Kevin applied for Deshe's job

         kevinJobListOfApplicants.add(kim); // Kim applied for Kevin's job
         kevinJobListOfApplicants.add(kevin); // Kevin applied for Kevin's job

         kimJobListOfApplicants.add(kevin); // Kevin applied for Kim's job
         kimJobListOfApplicants.add(deshe); // Deshe applied for Kim's job

         // for each job, assign the list of applicants
         dJob.setApplicantsList(desheJobListOfApplicants);
         kevJob.setApplicantsList(kevinJobListOfApplicants);
         kimJob.setApplicantsList(kimJobListOfApplicants);

         businessRepository.save(dBusiness);
         businessRepository.save(kevBusiness);
         businessRepository.save(kimBusiness);

         jobRepository.save(dJob);
         jobRepository.save(kevJob);
         jobRepository.save(kimJob);
      }
   }
}
