package jobboardapi.controller;

import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.service.MyProfileService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

   @DeleteMapping(path = "")
   public JSONObject deleteMyProfile() {
      return myProfileService.deleteMyProfile();
   }

   @GetMapping(path = "/jobs")
   public List<Job> getJobsIAppliedFor() {
      return myProfileService.getJobsIAppliedFor();
   }
}
