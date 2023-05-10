package jobboardapi.service;

import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
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
}
