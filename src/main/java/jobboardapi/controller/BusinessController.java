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

    @PutMapping("/{businessId}")
    public Business updateBusiness(@PathVariable Long businessId, @RequestBody Business businessBody) {
        return businessService.updateBusiness(businessId, businessBody);
    }
}
