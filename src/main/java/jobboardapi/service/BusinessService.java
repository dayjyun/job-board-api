package jobboardapi.service;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.BadRequestException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.repository.JobRepository;
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
    * NotFoundException is thrown
    * @return a list of businesses
    */
   public List<Business> getAllBusinesses() {
      List<Business> allBusinesses = businessRepository.findAll();
      if (allBusinesses.size() > 0) {
         return allBusinesses;
      } else {
         throw new NotFoundException("No businesses found");
      }
   }

   /**
    * createBusiness checks for the business name in the business database. If the name already exists, then the AlreadyExistsException is
    * thrown. If the name does not exist, the business objects gets saved to the database.
    * @param businessObject is the new business the user is creating.
    * @return the details of the new business.
    */
   public Business createBusiness(Business businessObject) {
      Optional<Business> business = businessRepository.findByName(businessObject.getName());
      if (business.isPresent()) {
         throw new AlreadyExistsException("Business with the name " + businessObject.getName() + " already exists.");
      } else {
         if (businessObject.getName() == "" || businessObject.getName() == null) {
            throw new BadRequestException("Business name is required");
         } else {
            businessObject.setUser(UserService.getLoggedInUser());
            return businessRepository.save(businessObject);
         }
      }
   }

   /**
    * getBusinessById retrieves the business by the business id, if the business id exists. If the business id does not exist, a
    * NotFoundException is thrown
    * @param businessId is what we're searching by
    * @return the optional of the business
    */
   public Optional<Business> getBusinessById(Long businessId) {
      Optional<Business> business = businessRepository.findById(businessId);
      if (business.isPresent()) {
         return business;
      } else {
         throw new NotFoundException("Business with id " + businessId + " not found");
      }
   }

   /**
    * updateBusiness updates the business by searching for a business's ID If the business id does not exist, a NotFoundException is thrown
    * If the business name after the update matches any existing businesses, an AlreadyExistsException is thrown
    * @param businessId   is our target business ID
    * @param businessBody updated business details
    * @return the updated Business object
    */
   public Business updateBusiness(Long businessId, Business businessBody) {
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, UserService.getLoggedInUser().getId());
      List<Business> allBusinesses = businessRepository.findAll();
      if (business.isPresent()) {
         Business updatedBusiness = businessRepository.findBusinessByIdAndUserId(businessId, UserService.getLoggedInUser().getId()).get();
         for (Business b : allBusinesses) {
            if (b.getName()
                 .equals(businessBody.getName())) {
               throw new AlreadyExistsException("Business name already exists");
            }
         }
         updatedBusiness.setName(businessBody.getName());
         updatedBusiness.setHeadquarters(businessBody.getHeadquarters());
         return businessRepository.save(updatedBusiness);
      } else {
         throw new NotFoundException("Business not found");
      }
   }

   /**
    * deleteBusiness checks if a business id is present in the business database for the logged-in user. If the business id does not
    * exist, a NotFoundException is
    * thrown. If the business id exists, the business is deleted from the database, and the deleted business's details are returned.
    * @param businessId is the id for business the user wants to delete
    * @return the deleted business's details
    */
   public Business deleteBusiness(Long businessId) {
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, UserService.getLoggedInUser().getId());
      if (business.isPresent()) {
         businessRepository.deleteById(businessId);
         return business.get();
      } else {
         throw new NotFoundException("Business with id " + businessId + " not found for user");
      }
   }

   /**
    * getJobByBusinessId retrieves a list of jobs by the business id. If the business id does not exist, a NotFoundException is thrown.
    * If the business has no jobs, a NotFoundException is thrown
    * @param businessId is what we're searching by
    * @return a list of jobs for the business
    */
   public List<Job> getJobByBusinessId(Long businessId) {
      Optional<Business> business = businessRepository.findById(businessId);
      if (business.isPresent()) {
         List<Job> jobList = business.get()
                                     .getListOfJobsAvailable();
         if(jobList.size() == 0) {
            throw new NotFoundException("No jobs posted for " + business.get().getName());
         }
         return business.get()
                        .getListOfJobsAvailable();
      } else {
         throw new NotFoundException("Business not found");
      }
   }

   /**
    * createJobForBusinessId checks for the business id in the business database. If the business id does not exist, the NotFoundException
    * is thrown. If the business id exists, then it checks to see if the job title exists in the business's job list. If the job title
    * exists, the AlreadyExistsException is thrown. If the job title does not exist, the job object is added to the business's job list, and
    * the user sees the new job's details.
    * @param businessId is the business the user is creating a job for
    * @param jobObject  is the new job the user is creating
    * @return the job object's information
    */
   public Job createJobForBusinessId(Long businessId, Job jobObject) {
      Optional<Business> business = businessRepository.findBusinessByIdAndUserId(businessId, UserService.getLoggedInUser().getId());
      if (business.isPresent()) {
         jobObject.setBusiness(business.get());
         business.get().getListOfJobsAvailable().add(jobObject);
         return jobRepository.save(jobObject);
      } else {
         throw new NotFoundException("Business not found");
      }
   }
}
