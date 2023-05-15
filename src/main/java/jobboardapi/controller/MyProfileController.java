package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.service.MyProfileService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/myProfile")
public class MyProfileController {
   @Autowired
   private MyProfileService myProfileService;

   // Functionality: Returns logged-in user's details
   // Path: http://localhost:8080//api/myProfile
   @GetMapping(path = "")
   public User getMyProfile() {
      return MyProfileService.getLoggedInUser();
   }

   // Functionality: Edit user account
   // Path: http://localhost:8080//api/myProfile
   @PutMapping(path = "")
   public User updateMyProfile(@RequestBody User updatedBody) {
      return myProfileService.updateMyProfile(updatedBody);
   }

   // Functionality: Delete user account
   // Path: http://localhost:8080//api/myProfile
   @DeleteMapping(path = "")
   public User deleteMyProfile() {
      return myProfileService.deleteMyProfile();
   }

   // Functionality: Returns a list of jobs the user applied for
   // Path: http://localhost:8080//api/myProfile/jobs
   @GetMapping(path = "/jobs")
   public List<Job> getJobsIAppliedFor() {
      return myProfileService.getJobsIAppliedFor();
   }
}
