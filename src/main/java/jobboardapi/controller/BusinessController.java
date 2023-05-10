package jobboardapi.controller;

import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.models.Business;
import jobboardapi.repository.BusinessRepository;
import jobboardapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    private BusinessRepository businessRepository;

    @Autowired
    public void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    // User Story: Returns a list of all businesses
    // http://localhost:8080/api/businesses
    @GetMapping(path = "")
    public List<Business> getAllBusinesses(){
        return businessService.getAllBusinesses();
    }

    // User Story: Create a business
    // http://localhost:8080/api/businesses
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Business createBusiness(@RequestBody Business businessObject){
        Business business = businessRepository.findByName(businessObject.getName());
        if (business != null){
            throw new AlreadyExistsException("Business with the name " + businessObject.getName() + " already exists.");
        } else {
            businessRepository.save(businessObject);
            return businessObject;
        }
  
    // User Story: I want to update my business's detail
    // http://localhost:8080/api/businesses/{businessId}
    @PutMapping("/{businessId}")
    public Business updateBusiness(@PathVariable Long businessId, @RequestBody Business businessBody) {
        return businessService.updateBusiness(businessId, businessBody);

    }
}
