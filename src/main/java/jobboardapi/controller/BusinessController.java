package jobboardapi.controller;

import jobboardapi.models.Business;
import jobboardapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // User Story: Update business details
    // http://localhost:8080/api/businesses/{businessId}
    @PostMapping(path = "/{businessId}")
    public Business updateBusinessById(@PathVariable Long businessId){
        return businessService.getBusinessById(businessId);
    }
}
