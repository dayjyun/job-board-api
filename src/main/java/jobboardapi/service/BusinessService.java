package jobboardapi.service;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    private BusinessRepository businessRepository;

    @Autowired
    public void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    /**
     * getAllBusinesses retrieves the list of all businesses from the business repository
     * @return a list of businesses
     */
    public List<Business> getAllBusinesses(){
        return businessRepository.findAll();
    }

    /**
     * createBusiness checks for the business name in the business database.
     * If the name already exists, then the AlreadyExistsException is thrown.
     * If the name does not exist, the business objects gets saved to the database.
     * @param businessObject is the new business the user is creating.
     * @return the details of the new business.
     */
    public Business createBusiness(Business businessObject){
          Business business = businessRepository.findByName(businessObject.getName());
          if (business != null) {
              throw new AlreadyExistsException("Business with the name " + businessObject.getName() + " already exists.");
          } else {
              businessRepository.save(businessObject);
              return businessObject;
          }
      }

     /**
     * getBusinessById retrieves the business by the business id, if the business id exists.
     * If the business id does not exist, we throw the NotFoundException
     * @param businessId is what we're searching by
     * @return the optional of the business
     */
    public Optional<Business> getBusinessById(Long businessId){
        Optional<Business> business = businessRepository.findById(businessId);
        if(business.isPresent()) {
            return business;
        } else {
            throw new NotFoundException("Business not found");
        }
    }
  
  /**
     * updateBusiness updates the business by searching for a business's ID and throws the NotFoundException if the business ID does not exist.
     * @param businessId is our target business ID
     * @param businessBody updated business details
     * @return An Optional of a Business object
     */
    public Business updateBusiness(Long businessId, Business businessBody) {
        Optional<Business> business = businessRepository.findById(businessId);
        if(business.isPresent()) {
            Business updatedBusiness = businessRepository.findById(businessId).get();
            updatedBusiness.setName(businessBody.getName());
            updatedBusiness.setHeadquarters(businessBody.getHeadquarters());
            return businessRepository.save(updatedBusiness);
        } else {
            throw new NotFoundException("Business not found");
        }
    }

    /**
     * deleteBusiness checks if a business id is present in the business database.
     * If the business id does not exist, NotFoundException is thrown.
     * If the business id exists, the business is deleted from the database, and the deleted business's details are returned.
     * @param businessId is the business the user wants to delete
     * @return the deleted business's details
     */
    public Business deleteBusiness(Long businessId) {
      Optional<Business> business = businessRepository.findById(businessId);
      if (business.isPresent()) {
          businessRepository.deleteById(businessId);
          return business.get();
      } else {
          throw new NotFoundException("Business not found");
      }
    }

    /**
     * getJobByBusinessId retrieves a list of jobs by the business id, if the business id exists.
     * If the business id does not exist, we throw the NotFoundException
     * @param businessId is what we're searching by
     * @return a list of jobs for the business
     */
    public List<Job> getJobByBusinessId(Long businessId){
        Optional<Business> business = businessRepository.findById(businessId);
        if (business.isPresent()) {
            return business.get().getJobList();
        } else {
            throw new NotFoundException("Business not found");
        }
    }

    /**
     * createJobForBusinessId checks for the business id in the business database.
     * If the business id does not exist, the NotFoundException is thrown.
     * If the business id exists, then it checks to see if the job title exists in the business's job list.
     * If the job title exists, the AlreadyExistsException is thrown.
     * If the job title does not exist, the job object is added to the business's job list,
     * and the user sees the new job's details.
     * @param businessId is the business the user is creating a job for
     * @param jobObject is the new job the user is creating
     * @return the job object's information
     */
    public Job createJobForBusinessId(Long businessId, Job jobObject){
        Optional<Business> business = businessRepository.findById(businessId);
        if (business.isPresent()) {
            if (!business.get().getJobList().contains(jobObject.getTitle())){
                business.get().getJobList().add(jobObject);
                return jobObject;
            } else {
                throw new AlreadyExistsException("The job title for this business already exists");
            }
        } else {
            throw new NotFoundException("Business not found");
        }
    }
}
