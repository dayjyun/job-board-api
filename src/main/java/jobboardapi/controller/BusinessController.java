package jobboardapi.controller;

import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
  
    // Functionality: Returns a list of all businesses
    // http://localhost:8080/api/businesses
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Business createBusiness(@RequestBody @Valid Business businessObject) {
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
    public Business updateBusiness(@PathVariable Long businessId, @RequestBody @Valid Business businessBody) {
        return businessService.updateBusiness(businessId, businessBody);
    }

    // User Story: I want to delete my business
    // http://localhost:8080/api/businesses/{businessId}
    @DeleteMapping(path = "/{businessId}")
    public Business deleteBusiness(@PathVariable Long businessId) {
        return businessService.deleteBusiness(businessId);
    }

    // User Story: I want to see a list of all job listings for a specific business
    // http://localhost:8080/api/businesses/{businessId}/jobs
    @GetMapping(path = "/{businessId}/jobs")
    public List<Job> getJobByBusinessId(@PathVariable Long businessId) {
        return businessService.getJobByBusinessId(businessId);
    }

    // User Story: Create a new job listing for the business
    // http://localhost:8080/api/businesses/{businessId}/jobs
    @PostMapping(path = "/{businessId}/jobs")
    @ResponseStatus(HttpStatus.CREATED)
    public Job createJobForBusinessId(@PathVariable Long businessId, @RequestBody @Valid Job jobObject) {
        return businessService.createJobForBusinessId(businessId, jobObject);
    }
}
