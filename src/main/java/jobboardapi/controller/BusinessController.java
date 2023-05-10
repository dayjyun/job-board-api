package jobboardapi.controller;

import jobboardapi.models.Business;
import jobboardapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(path = "/{businessId}")
    public Optional<Business> getBusinessById(@PathVariable Long businessId) {
        return businessService.getBusinessById(businessId);
    }

    @DeleteMapping(path = "/{businessId}")
    public Business deleteBusiness(@PathVariable Long businessId) {
        return businessService.deleteBusiness(businessId);
    }
}
