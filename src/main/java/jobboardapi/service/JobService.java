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
      Optional<Job> jobToUpdate = jobRepository.findById(jobId);
      if (jobToUpdate.isPresent()) {
         // Check that the logged-in user has a business where the id for the business matches the business_id in relation to the job's id
         Optional<Business> loggedInUserBusiness = businessRepository.findBusinessByIdAndUserId(
                 jobRepository.findById(jobId).get().getBusiness().getId(),
                 UserService.getLoggedInUser().getId()
         );
         // While the user has the targeted business
         if (loggedInUserBusiness.isPresent()) {
            // Search for the list of jobs belonging to the targeted business
            List<Job> jobListForBusiness = loggedInUserBusiness.get().getListOfJobsAvailable();
            // While we have a list of jobs for the targeted business
            if (jobListForBusiness.size() > 0) {
               for (Job job : jobListForBusiness) {
                  if (job.getId().equals(jobId)) {
                     job.setTitle(jobBody.getTitle());
                     job.setDescription(jobBody.getDescription());
                     job.setLocation(jobBody.getLocation());
                     job.setSalary(jobBody.getSalary());
                     return jobRepository.save(job);
                  }
               }
            }
            throw new NotFoundException("No jobs found for business with id " + loggedInUserBusiness.get().getId());
         }
         throw new NotFoundException("Business with id " + loggedInUserBusiness.get().getId() + " not found");
      }
      throw new NotFoundException("Job with id " + jobId + " not found");
   }

   /**
    * )    * deleteJobListing checks if a job id is present in the job database. Next, it checks if the businesses has a list of jobs
    * available. Following that, it checks if any of the job's business id matches the business id for the business owned by the user Once
    * it passes the check, the listing gets updated If any of the checks fail, a NotFoundException is thrown
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
            List<Job> jobListForBusiness = loggedInUserBusiness.get().getListOfJobsAvailable();
            for (Job job : jobListForBusiness) {
               if (Objects.equals(job.getId(), jobId)) {
                  jobRepository.deleteById(jobId);
                  return jobListing.get();
               }
            }
            throw new NotFoundException("Job listing with id" + jobId + " not found");
         }
         throw new NotFoundException("Business with id " + jobRepository.findById(jobId).get().getBusiness()
                                                                        .getId() + " does not belong to user");
      }
      throw new NotFoundException("Job listing with id" + jobId + " not found");
   }

   /**
    * getListOfApplicants retrieves the list of all users that have applied to the job listing If the job id does not exist, a
    * NotFoundException is thrown If the user list is empty, a NotFoundException is thrown A user is only able to see a list of applicants
    * for a business they own.
    *
    * @param jobId is the id for the job the user wants to check the applicants for
    * @return a list of applicants for the targeted job
    */
   public List<User> getListOfApplicants(Long jobId) {
      Optional<Job> jobListing = jobRepository.findById(jobId);
      User user = UserService.getLoggedInUser();
      if (jobListing.isPresent()) {
         if (jobListing.get().getUser().getId().equals(user.getId())) {
            List<User> applicantsList = jobListing.get().getApplicantsList();
            if (applicantsList.size() > 0) {
               return jobListing.get().getApplicantsList();
            } else {
               throw new NotFoundException("No applicants found");
            }
         } else {
            throw new NotFoundException("Job with id " + jobId + " not found for user");
         }
      } else {
         throw new NotFoundException("Job with id " + jobId + " not found");
      }
   }

   public Optional<Job> applyForJobListing(Long jobId) {
      Optional<Job> jobListing = jobRepository.findById(jobId);
      User applicant = UserService.getLoggedInUser();
      if (jobListing.isPresent()) {
         List<User> applicantsList = jobListing.get().getApplicantsList();
         if (!applicantsList.contains(applicant)) {
            applicantsList.add(applicant);
            jobListing.get().setApplicantsList(applicantsList);

            applicant.getListOfJobsAppliedTo().add(jobListing.get());
            applicant.setJob(jobListing.get());
            jobRepository.save(jobListing.get());
            userRepository.save(applicant);
            return jobListing;
         }
         throw new AlreadyExistsException("Application already submitted");
      }
      throw new NotFoundException("Job listing not found");
   }
}