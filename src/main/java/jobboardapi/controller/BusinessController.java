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
    public Business createBusiness(@RequestBody Business businessObject){
        Business business = businessService.getBusinessById()
        .findByName(categoryObject.getName());
        if (category != null){
            throw new InformationExistException("Category with the name " + categoryObject.getName() + " already exists.");
        } else {
            return categoryRepository.save(categoryObject);
        }
    }
}
