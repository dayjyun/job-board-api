package jobboardapi.service;

import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.Business;
import jobboardapi.models.User;
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
}
