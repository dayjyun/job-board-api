package jobboardapi.service;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.BadRequestException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.repository.JobRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

   private BusinessRepository businessRepository;
   private JobRepository jobRepository;

   @Autowired
   public void setBusinessRepository(BusinessRepository businessRepository) {
      this.businessRepository = businessRepository;
   }

   @Autowired
   public void setJobRepository(JobRepository jobRepository) {
      this.jobRepository = jobRepository;
   }

   /**
    * getAllBusinesses retrieves the list of all businesses from the business repository. If there are no businesses in the database, a
    * NotFoundException is thrown.
    *
    * @return a list of businesses.
    */
   public List<Business> getAllBusinesses() {
      List<Business> allBusinesses = businessRepository.findAll();
      // Check there is a list of businesses
      if (allBusinesses.size() > 0) {
         // Returns all the businesses in the database
         return allBusinesses;
      } else {
         // Throws an error when there are no businesses in the database
         throw new NotFoundException("No businesses found");
      }
   }

   /**
    * createBusiness checks for the business name in the business database. If the name already exists, then the AlreadyExistsException is
    * thrown. If the name does not exist, the business objects gets saved to the database.
    *
    * @param businessObject is the new business the user is creating.
    * @return the details of the new business.
    */
   public Business createBusiness(Business businessObject) {
      // Look for a businesses with the same name as our new business
      Optional<Business> business = businessRepository.findByName(businessObject.getName());
      // Check if the search is true, throw an error
      if (business.isPresent()) {
         throw new AlreadyExistsException("Business with the name " + businessObject.getName() + " already exists.");
      } else {
         // check that the name field is not empty
         if (businessObject.getName() == "" || businessObject.getName() == null) {
            throw new BadRequestException("Business name is required");
         } else {
            // create business and assign our logged-in user as the owner of the business
            businessObject.setUser(MyProfileService.getLoggedInUser());
            // Save the newly added business to the database
            return businessRepository.save(businessObject);
         }
      }
   }

   /**
    * getBusinessById retrieves the business by the business id, if the business id exists. If the business id does not exist, a
    * NotFoundException is thrown.
    *
    * @param businessId is what we're searching by.
    * @return the optional of the business.
    */
   public Optional<Business> getBusinessById(Long businessId) {
      // Search for a business using its ID
      Optional<Business> business = businessRepository.findById(businessId);
      // If business is found, return the business's data
      if (business.isPresent()) {
         return business;
      } else {
         // Throw an error if the business is not found in the database
         throw new NotFoundException("Business with id " + businessId + " not found");
      }
   }

   /**
    * updateBusiness updates the business by searching for a business's ID If the business id does not exist, a NotFoundException is thrown
    * If the business name after the update matches any existing businesses, an AlreadyExistsException is thrown.
    *
    * @param businessId   is our target business ID.
    * @param businessBody updated business details.
    * @return the updated Business object.
    */
   public Business updateBusiness(Long businessId, Business businessBody) {
      // Find the business by using its ID that belongs to the logged-in user
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, MyProfileService.getLoggedInUser().getId());
      // Return a list of all the businesses in the database
      List<Business> allBusinesses = businessRepository.findAll();
      // If our target business is found
      if (business.isPresent()) {
         // Obtain the business's data
         Business updatedBusiness = businessRepository.findBusinessByIdAndUserId(businessId, MyProfileService.getLoggedInUser().getId())
                                                      .get();
         // Search through the list of all businesses
         for (Business b : allBusinesses) {
            // If any business matches the name we're attempting to update our business to, throw an error
            if (b.getName().equals(businessBody.getName())) {
               throw new AlreadyExistsException("Business name already exists");
            }
         }
         // Check that the name field is not empty when updating the name
         if (businessBody.getName() != null && !businessBody.getName().isEmpty()) {
            updatedBusiness.setName(businessBody.getName());
         }
         // Check that the headquarters field is not empty when updating the headquarters
         if (businessBody.getHeadquarters() != null && !businessBody.getHeadquarters().isEmpty()) {
            updatedBusiness.setHeadquarters(businessBody.getHeadquarters());
         }
         // Save the business with the updated data
         return businessRepository.save(updatedBusiness);
      } else {
         // Throw an error if the business is not found in the database
         throw new NotFoundException("Business with id " + businessId + " not found");
      }
   }

   /**
    * deleteBusiness checks if a business id is present in the business database for the logged-in user. If the business id does not exist,
    * a NotFoundException is thrown. If the business id exists, the business is deleted from the database, and the deleted business's
    * details are returned.
    *
    * @param businessId is the id for business the user wants to delete.
    * @return the deleted business's details.
    */
   public JSONObject deleteBusiness(Long businessId) {
      // Find the business by using its ID that belongs to the logged-in user
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, MyProfileService.getLoggedInUser().getId());
      // Create an object to return a custom message
      JSONObject returnMessage = new JSONObject();
      // Create custom message and add it to our custom message object
      returnMessage.put("message", "Business with id " + businessId + " successfully deleted");
      // Check if the targeted business is found
      if (business.isPresent()) {
         // Delete the targeted business
         businessRepository.deleteById(businessId);
         // Return the custom message after a successful deletion
         return returnMessage;
      } else {
         // Throw an error if the business is not found in the database
         throw new NotFoundException("Business with id " + businessId + " not found for user");
      }
   }

   /**
    * getJobByBusinessId retrieves a list of jobs by the business id. If the business id does not exist, a NotFoundException is thrown. If
    * the business has no jobs, a NotFoundException is thrown.
    *
    * @param businessId is what we're searching by.
    * @return a list of jobs for the business.
    */
   public List<Job> getJobByBusinessId(Long businessId) {
      // Find a business by its ID
      Optional<Business> business = businessRepository.findById(businessId);
      // Check if the targeted business is found
      if (business.isPresent()) {
         // Return a list of all jobs pertaining to the targeted business
         List<Job> jobList = business.get().getListOfJobsAvailable();
         // If the targeted business has no jobs available, throw an error
         if (jobList.size() == 0) {
            throw new NotFoundException("No jobs posted for " + business.get().getName());
         }
         // Return the list of jobs from our targeted business
         return business.get()
                        .getListOfJobsAvailable();
      } else {
         // Throw an error if the business is not found in the database
         throw new NotFoundException("Business with id " + businessId + " not found");
      }
   }

   /**
    * createJobForBusinessId checks for the business id in the business database. If the business id does not exist, the NotFoundException
    * is thrown. If the business id exists, then it checks to see if the job title exists in the business's job list. If the job title
    * exists, the AlreadyExistsException is thrown. If the job title does not exist, the job object is added to the business's job list, and
    * the user sees the new job's details.
    *
    * @param businessId is the business the user is creating a job for.
    * @param jobObject  is the new job the user is creating.
    * @return the job object's information.
    */
   public Job createJobForBusinessId(Long businessId, Job jobObject) {
      // Find the business by using its ID that belongs to the logged-in user
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, MyProfileService.getLoggedInUser().getId());
      // Check if the targeted business is found
      if (business.isPresent()) {
         // Check that the title field is not empty when updating the title
         if (jobObject.getTitle() == null || jobObject.getTitle() == "") {
            throw new BadRequestException("Job title is required");
         } // Check that the location field is not empty when updating the location
         if (jobObject.getLocation() == null || jobObject.getLocation() == "") {
            throw new BadRequestException("Job location is required");
         }
         // Assign the job to the correspoding business
         jobObject.setBusiness(business.get());
         // Add the job to the list of job listings the business contains
         business.get().getListOfJobsAvailable().add(jobObject);
         // Save the job listing
         return jobRepository.save(jobObject);
      }
      // Throw an error if the business is not found in the database
      throw new NotFoundException("Business with id " + businessId + " not found");
   }
}
