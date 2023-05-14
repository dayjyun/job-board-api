package jobboardapi.controller;

import jobboardapi.models.User;
import jobboardapi.service.MyProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/myProfile")
public class MyProfileController {
   @Autowired
   private MyProfileService myProfileService;

   @GetMapping(path = "")
   public User getMyProfile() {
      return MyProfileService.getLoggedInUser();
   }

//   @PutMapping(path = "")


//   @DeleteMapping(path = "")


//   @GetMapping(path = "/jobs")
}
