package jobboardapi.service;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.repository.JobRepository;
import jobboardapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobService {

   private JobRepository jobRepository;

   @Autowired
   public void setJobRepository(JobRepository jobRepository) {
      this.jobRepository = jobRepository;
   }

   private UserRepository userRepository;

   @Autowired
   public void setUserRepository(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   private BusinessRepository businessRepository;

   @Autowired
   public void setBusinessRepository(BusinessRepository businessRepository) {
      this.businessRepository = businessRepository;
   }

   /**
    * getAllJobs retrieves the list of all jobs from the job repository. A NotFoundException is thrown if no jobs are stored in the
    * database
    *
    * @return a list of jobs
    */
   public List<Job> getAllJobListings() {
      // Find a list of jobs in the database
      List<Job> allJobsList = jobRepository.findAll();
      // While we have a list, return the list
      if (allJobsList.size() > 0) {
         return allJobsList;
      } else {
         throw new NotFoundException("No jobs found");
      }
   }

   /**
    * getJobListingById retrieves the job by the job id, if the job id exists If the job id does not exist, a NotFoundException is thrown
    *
    * @param jobId is what we're searching by
    * @return the optional of the job
    */
   public Optional<Job> getJobListingById(Long jobId) {
      // Search for a job by its ID
      Optional<Job> job = jobRepository.findById(jobId);
      // When job is found, return the job
      if (job.isPresent()) {
         return job;
      } else {
         throw new NotFoundException("Job listing not found");
      }
   }

   /**
    * updateJobListing updates the job by searching for a job's ID. It first checks for the business's id and the current logged-in user's
    * id to make sure the user is the owner for the business. Next, it checks if the businesses has a list of jobs available. Following
    * that, it checks if any of the job's business id matches the business id for the business owned by the user Once it passes the check,
    * the listing gets updated If any of the checks fail, a NotFoundException is thrown
    *
    * @param jobId   is our target job ID
    * @param jobBody updated job details
    * @return the updated Job object
    */
   public Job updateJobListing(Long jobId, Job jobBody) {
      // Check that the logged-in user has a business where the id for the business matches the business_id in relation to the job's id
      Optional<Business> loggedInUserBusiness =
              businessRepository.findBusinessByIdAndUserId(jobRepository.findById(jobId)
                                                                        .get()
                                                                        .getBusiness()
                                                                        .getId(), UserService.getLoggedInUser()
                                                                                             .getId());
      // // While the user has the targeted business
      if (loggedInUserBusiness.isPresent()) {
         // Search for the list of jobs belonging to the targeted business
         List<Job> jobListForBusiness = loggedInUserBusiness.get().getJobList();
         // While we have a list of jobs for the targeted business
         if (jobListForBusiness.size() > 0) {
            // While we have a list of jobs for the targeted business
            for (Job job : jobListForBusiness) {
               // Find the job listing that matches the jobId
               if (Objects.equals(job.getId(), jobId)) {
                  // Update the job listing
                  Job updatedJobListing = jobRepository.findById(jobId).get();
                  updatedJobListing.setTitle(jobBody.getTitle());
                  updatedJobListing.setDescription(jobBody.getDescription());
                  updatedJobListing.setLocation(jobBody.getLocation());
                  updatedJobListing.setSalary(jobBody.getSalary());
                  return jobRepository.save(updatedJobListing);
               }
            }
            throw new NotFoundException("Job listing not found");
         } else {
            throw new NotFoundException("Business has no job listings");
         }
      } else {
         throw new NotFoundException("Business not found");
      }
   }

   /**
    * deleteJobListing checks if a job id is present in the job database. Next, it checks if the businesses has a list of jobs available.
    * Following that, it checks if any of the job's business id matches the business id for the business owned by the user Once it passes
    * the check, the listing gets updated If any of the checks fail, a NotFoundException is thrown
    *
    * @param jobId is the id for the job the user wants to delete
    * @return the deleted job's details
    */
   public Job deleteJobListing(Long jobId) {
      // Find the targeted job listing
      Optional<Job> jobListing = jobRepository.findById(jobId);
      // When the job listing is found
      if (jobListing.isPresent()) {
         // 
         Optional<Business> loggedInUserBusiness = businessRepository.findBusinessByIdAndUserId(jobListing.get()
                                                                                                          .getBusiness()
                                                                                                          .getId(),
                 UserService.getLoggedInUser()
                            .getId());
         if (loggedInUserBusiness.isPresent()) {
            List<Job> jobListForBusiness = loggedInUserBusiness.get().getJobList();
            for (Job job : jobListForBusiness) {
               if (Objects.equals(job.getId(), jobId)) {
                  jobRepository.deleteById(jobId);
                  return jobListing.get();
               }
            }
            throw new NotFoundException("Job listing not found");
         } else {
            throw new NotFoundException("Business has no job listings");
         }
      } else {
         throw new NotFoundException("Job listing not found");
      }
   }

   /**
    * getListOfApplicants retrieves the list of all users that have applied to the job listing If the job id does not exist, a
    * NotFoundException is thrown If the user list is empty, a NotFoundException is thrown
    *
    * @param jobId is the id for the job the user wants to check the applicants for
    * @return a list of applicants for the targeted job
    */
   public List<User> getListOfApplicants(Long jobId) {
      // Check that the logged-in user has a business where the id for the business matches the business_id in relation to the job's id
      Optional<Business> loggedInUserBusiness =
              businessRepository.findBusinessByIdAndUserId(jobRepository.findById(jobId)
                                                                        .get()
                                                                        .getBusiness()
                                                                        .getId(), UserService.getLoggedInUser()
                                                                                             .getId());

      // While the user has the targeted business
      if (loggedInUserBusiness.isPresent()) {
         // Search for the list of jobs belonging to the targeted business
         List<Job> jobList = loggedInUserBusiness.get().getJobList();
         // While we have a list of jobs for the targeted business
         if (jobList.size() > 0) {
            // Search for the matching job id for our jobId
            for (Job job : jobList) {
               // Find the job listing that matches the jobId
               if (Objects.equals(job.getId(), jobId)) {
                  // Return the list of applicants for the job
                  return job.getApplicantsList();
               } else {
                  throw new NotFoundException("No applicants for job");
               }
            }
            throw new NotFoundException("Job listing not found");
         } else {
            throw new NotFoundException("No job listings found");
         }
      } else {
         throw new NotFoundException("No businesses found");
      }
   }

   /**
    *
    */
   public Optional<Job> applyForJobListing(Long jobId, User userBody) {
      Optional<Job> jobListing = jobRepository.findById(jobId);
      if (jobListing.isPresent()) {
//            User user = userRepository.findUserByEmail(userBody.getEmail());
//            Optional<User> user = jobRepository.findByUserEmail(userBody.getEmail());
//            Optional<User> user = jobRepository.findByUserId(userBody.getId());
//            Optional<User> user = jobRepository.findJobByIdAndAndUserId(jobId, userBody.getId());
//            Optional<User> user = userRepository.findUserByIdAndJobId(jobId, userBody.getId());
//            if(user.isPresent()) {
            if(jobListing.get().getUser() == userBody){
                throw new AlreadyExistsException("Application already submitted");
            } else {
                jobListing.get().setApplied(true);
                User applicant = userRepository.findById(userBody.getId()).get();
                applicant.setId(userBody.getId());
                applicant.setName(userBody.getName());
                applicant.setEmail(userBody.getEmail());
                applicant.setResume(userBody.getResume());
//                applicant.getJobList().add(jobRepository.findById(jobId).get());
                jobListing.get().getApplicantsList().add(applicant);
                jobRepository.save(jobListing.get());
                return jobListing;
            }
        } else {
            throw new NotFoundException("Job listing not found");
        }
//        if(jobListing.isPresent()) {
//            Job job = jobListing.get();
//            List<User> applicantsList = job.getApplicantsList();
//            Optional<User> user = applicantsList.stream().filter(applicant -> applicant.getId().equals(userBody.getId())).findFirst();
//
//            if(user.isPresent()) {
//                throw new AlreadyExistsException("Application already submitted");
//            } else {
//                job.setApplied(true);
//                applicantsList.add(userBody);
//                jobRepository.save(job);
//                return jobListing;
//            }
//        } else {
//            throw new NotFoundException("Job listing not found");
//        }
   }
}
