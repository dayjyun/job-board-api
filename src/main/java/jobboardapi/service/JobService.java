package jobboardapi.service;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.repository.JobRepository;
import net.minidev.json.JSONObject;
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

   private BusinessRepository businessRepository;

   @Autowired
   public void setBusinessRepository(BusinessRepository businessRepository) {
      this.businessRepository = businessRepository;
   }

   /**
    * getAllJobs retrieves the list of all jobs from the job repository. A NotFoundException is thrown if no jobs are stored in the
    * database.
    *
    * @return a list of jobs.
    */
   public List<Job> getAllJobListings() {
      // Find a list of jobs in the database
      List<Job> allJobsList = jobRepository.findAll();
      // While we have a list
      if (allJobsList.size() > 0) {
         // Returns all the jobs in the database
         return allJobsList;
      } else {
         // Throws an error when there are no jobs in the database
         throw new NotFoundException("No jobs found");
      }
   }

   /**
    * getJobListingById retrieves the job by the job id, if the job id exists If the job id does not exist, a NotFoundException is thrown.
    *
    * @param jobId is what we're searching by.
    * @return the optional of the job.
    */
   public Optional<Job> getJobListingById(Long jobId) {
      // Search for a job by its ID
      Optional<Job> job = jobRepository.findById(jobId);
      // When job is found
      if (job.isPresent()) {
         // Return the job data
         return job;
      } else {
         // Throw an error if the job is not found
         throw new NotFoundException("Job with id " + jobId + " not found");
      }
   }

   /**
    * updateJobListing updates the job by searching for a job's ID. It first checks for the business's id and the current logged-in user's
    * id to make sure the user is the owner for the business. Next, it checks if the businesses has a list of jobs available. Following
    * that, it checks if any of the job's business id matches the business id for the business owned by the user Once it passes the check,
    * the listing gets updated If any of the checks fail, a NotFoundException is thrown.
    *
    * @param jobId   is our target job ID.
    * @param jobBody updated job details.
    * @return the updated Job object.
    */
   public Job updateJobListing(Long jobId, Job jobBody) {
      Optional<Job> jobToUpdate = jobRepository.findById(jobId);
      if (jobToUpdate.isPresent()) {
         // Check that the logged-in user has a business where the id for the business matches the business_id in relation to the job's id
         Optional<Business> loggedInUserBusiness = businessRepository.findBusinessByIdAndUserId(
                 jobRepository.findById(jobId).get().getBusiness().getId(),
                 MyProfileService.getLoggedInUser().getId()
         );
         // While the user has the targeted business
         if (loggedInUserBusiness.isPresent()) {
            // Search for the list of jobs belonging to the targeted business
            List<Job> jobListForBusiness = loggedInUserBusiness.get().getListOfJobsAvailable();
            // While we have a list of jobs for the targeted business
            if (jobListForBusiness.size() > 0) {
               // Search through the list of all jobs within the targeted business
               for (Job job : jobListForBusiness) {
                  // Check if any IDs of the existing jobs match the input ID
                  if (job.getId().equals(jobId)) {
                     // Check that the title field is not empty when updating the title
                     if(jobBody.getTitle() != null && !jobBody.getTitle().isEmpty()) {
                     job.setTitle(jobBody.getTitle());
                     }
                     // Check that the description field is not empty when updating the description
                     if(jobBody.getDescription() != null && !jobBody.getDescription().isEmpty()) {
                        job.setDescription(jobBody.getDescription());
                     }
                     // Check that the location field is not empty when updating the location
                     if(jobBody.getLocation() != null && !jobBody.getLocation().isEmpty()) {
                        job.setLocation(jobBody.getLocation());
                     }
                     // Check that the salary field is not empty and is greater than 0 when updating the salary
                     if(jobBody.getSalary() != null && jobBody.getSalary() > 0) {
                        job.setSalary(jobBody.getSalary());
                     }
                     // Update the targeted job
                     return jobRepository.save(job);
                  }
               }
            }
            // Throw an error if there are no jobs for the targeted business
            throw new NotFoundException("No jobs found for business with id " + loggedInUserBusiness.get().getId());
         }
         // Throw an error if the business for the logged-in user is not found
         throw new NotFoundException("Business with id " + loggedInUserBusiness.get().getId() + " not found");
      }
      // Throw an error if the job ID doesn't exist in the database
      throw new NotFoundException("Job with id " + jobId + " not found");
   }

   /**
    * deleteJobListing checks if a job id is present in the job database. Next, it checks if the businesses has a list of jobs
    * available. Following that, it checks if any of the job's business id matches the business id for the business owned by the user Once
    * it passes the check, the listing gets updated If any of the checks fail, a NotFoundException is thrown
    *
    * @param jobId is the id for the job the user wants to delete.
    * @return the deleted job's details.
    */
   public JSONObject deleteJobListing(Long jobId) {
      // Find the targeted job listing
      Optional<Job> jobListing = jobRepository.findById(jobId);
      // When the job listing is found
      if (jobListing.isPresent()) {
         // Check that the logged-in user has a business where the id for the business matches the business_id in relation to the job's id
         Optional<Business> loggedInUserBusiness = businessRepository.findBusinessByIdAndUserId(
                                                                        jobListing.get().getBusiness().getId(),
                 MyProfileService.getLoggedInUser().getId());
         // If the business is found for the logged-in user
         if (loggedInUserBusiness.isPresent()) {
            // Search for the list of jobs the business has available
            List<Job> jobListForBusiness = loggedInUserBusiness.get().getListOfJobsAvailable();
            // Iterate through the jobs found within the list of jobs the business has available
            for (Job job : jobListForBusiness) {
               // Check if any of the IDs for those jobs match our targeted job's ID
               if (Objects.equals(job.getId(), jobId)) {
                  // Delete the job
                  jobRepository.deleteById(jobId);
                  // Create a custom message object
                  JSONObject returnMessage = new JSONObject();
                  // Add the custom message to the custom message object
                  returnMessage.put("message", "Job listing with id " + jobId + " successfully deleted");
                  // Return the message after a successful deletion
                  return returnMessage;
               }
            }
            // Throw an error if the targeted job is not found by its ID
            throw new NotFoundException("Job listing with id " + jobId + " not found");
         }
         // Throw an error if the targeted job does not belong to the logged-in user
         throw new NotFoundException("Job listing with id " + jobRepository.findById(jobId).get().getBusiness().getId() + " does not belong to user");
      }
      // Throw an error if the targeted job is not found by its ID
      throw new NotFoundException("Job listing with id " + jobId + " not found");
   }

   /**
    * getListOfApplicants retrieves the list of all users that have applied to the job listing If the job id does not exist, a
    * NotFoundException is thrown If the user list is empty, a NotFoundException is thrown A user is only able to see a list of applicants
    * for a business they own.
    *
    * @param jobId is the id for the job the user (business owner) wants to check the applicants for
    * @return a list of applicants for the targeted job
    */
   public List<User> getListOfApplicants(Long jobId) {
      // Find the targeted job by its ID
      Optional<Job> job = jobRepository.findById(jobId);
      // Check that the job is found
      if(job.isPresent()) {
         // Obtain the information for the business that the targeted job belongs to
         Optional<Business> business = Optional.ofNullable(job.get().getBusiness());
         // Check that the business is found, and the owner of the business is the logged-in user
         if(business.isPresent() && business.get().getUser().getId().equals(MyProfileService.getLoggedInUser().getId())) {
            // Find the list of applicants for the targeted job
            List<User> applicantsList = jobRepository.findById(jobId).get().getApplicantsList();
            // Make sure that the list is not empty
            if (!applicantsList.isEmpty()) {
               // Return the list
               return applicantsList;
            } else {
               // Throw an error if the list of applicants is empty
               throw new NotFoundException("No applicants found");
            }
         } else {
            // Throw an error if there are no businesses that belong to the user under the business ID
            throw new NotFoundException("Business with id " + business.get().getId() + " does not belong to user");
         }
      } else {
         // Throw an error if the job's ID is not found for the user
         throw new NotFoundException("Job with id " + jobId + " not found for user");
      }
   }

   /**
    *
    * @param jobId is the id for the job the user wants to apply for
    * @return job listing details after submitting user's information
    */
   public JSONObject applyForJobListing(Long jobId) {
      // Get the logged-in user's data
      User applicant = MyProfileService.getLoggedInUser();
      // Get the job listing based on the targeted ID
      Optional<Job> jobListing = jobRepository.findById(jobId);

      // Check the targeted job is found
      if (jobListing.isPresent()) {
         // Get the list of applicants that exist for the targeted job
         List<User> jobListingApplicants = jobListing.get().getApplicantsList();
         // Check that the logged-in user's ID does not match any existing ID in the list of applicants
         if (!jobListingApplicants.stream().anyMatch(a -> a.getId().equals(applicant.getId()))) {
            // Add the logged-in user to the list of applicants for the targeted job
            jobListingApplicants.add(applicant);
            // Modify the list for the targeted job with the newly added applicant (logged-in user)
            jobListing.get().setApplicantsList(jobListingApplicants);
            // Save the data for the targeted job
            jobRepository.save(jobListing.get());

            // Create a custom message object
            JSONObject returnMessage = new JSONObject();
            // Create a custom message and add it to our custom message object
            returnMessage.put("message", "Successfully applied to job with id " + jobId);
            // Return the customer message after a successful application submission
            return returnMessage;
         }
         // Throw an error if the user already exists within the list of applicants
         throw new AlreadyExistsException("Application already submitted for job with id " + jobId);
      }
      // Throw an error if the targeted job does not exist
      throw new NotFoundException("Job listing with id " + jobId + " not found");
   }
}