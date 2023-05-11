package jobboardapi.controller;

import jobboardapi.models.Business;
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
    public Business createBusiness(@RequestBody Business businessObject) {
        return businessService.createBusiness(businessObject);
    }

    // User Story: I want to view business details
    // http://localhost:8080/api/businesses/{businessId}
    @GetMapping(path = "/{businessId}")
    public Optional<Business> getBusinessById(@PathVariable Long businessId) {
        return businessService.getBusinessById(businessId);
    }
  
    // User Story: I want to update my business's detail
    // http://localhost:8080/api/businesses/{businessId}
    @PutMapping(path = "/{businessId}")
    public Business updateBusiness(@PathVariable Long businessId, @RequestBody Business businessBody) {
        return businessService.updateBusiness(businessId, businessBody);
    }

    // User Story: I want to delete my business
    // http://localhost:8080/api/businesses/{businessId}
    @DeleteMapping(path = "/{businessId}")
    public Business deleteBusiness(@PathVariable Long businessId) {
        return businessService.deleteBusiness(businessId);
    }
}
