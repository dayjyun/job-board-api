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

   @PutMapping(path = "")
   public User updateMyProfile(@RequestBody User updatedBody) {
      return myProfileService.updateMyProfile(updatedBody);
   }

//   @DeleteMapping(path = "")


//   @GetMapping(path = "/jobs")
}
