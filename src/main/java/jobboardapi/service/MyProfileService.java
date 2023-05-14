package jobboardapi.service;

import jobboardapi.exceptions.BadRequestException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.exceptions.UnauthorizedException;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.MyUserDetails;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class MyProfileService {

   @Autowired
   private UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;

   public MyProfileService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
      this.passwordEncoder = passwordEncoder;
      this.userRepository = userRepository;
   }

   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public static User getLoggedInUser() {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if(userDetails.getUser() == null || userDetails.getUsername().isEmpty() || userDetails.getUsername() == null) {
         throw new UnauthorizedException("Unauthorized");
      }
      return userDetails.getUser();
   }

   /**
    * Updates but you need to generate a new JWT
    *
    * @param updatedBody
    * @return
    */
   public User updateMyProfile(User updatedBody) {
      Optional<User> user = userRepository.findById(getLoggedInUser().getId());
      if (user.isPresent()) {
         // check that the email does not already in the database, and it's not the same as current email
         if (userRepository.existsByEmail(updatedBody.getEmail()) && !user.get().getEmail().equals(updatedBody.getEmail())) {
            throw new BadRequestException("Email already in use");
         }
         if (updatedBody.getPassword() != null && !updatedBody.getPassword().isEmpty()) {
            user.get().setPassword(passwordEncoder.encode(updatedBody.getPassword()));
         }
         if (updatedBody.getName() != null && !updatedBody.getName().isEmpty()) {
            user.get().setName(updatedBody.getName());
         }
         if (updatedBody.getEmail() != null && !updatedBody.getEmail().isEmpty()) {
            user.get().setEmail(updatedBody.getEmail());
         }
         // If the value of the resume is an empty string, it will delete the resume from the profile
         if (updatedBody.getResume() != null) {
            user.get().setResume(updatedBody.getResume());
         }
         return userRepository.save(user.get());
      } else {
         return null;
      }
   }

   public JSONObject deleteMyProfile() {
      JSONObject returnMessage = new JSONObject();
      returnMessage.put("message", "Account successfully deleted");
      Optional<User> myProfile = userRepository.findById(getLoggedInUser().getId());
      if (myProfile.isPresent()) {
         userRepository.deleteById(getLoggedInUser().getId());
         return returnMessage;
      } else {
         throw new NotFoundException("Odd. That wasn't supposed to happen.");
      }
   }

   public List<Job> getJobsIAppliedFor() {
      Optional<User> myProfile = userRepository.findById(getLoggedInUser().getId());
      if(myProfile.isPresent()) {
         List<Job> listOfJobsIAppliedTo = myProfile.get().getListOfJobsAppliedTo();;
         if(listOfJobsIAppliedTo.size() > 0) {
            return listOfJobsIAppliedTo;
         } else {
            throw new NotFoundException("No jobs applied to");
         }
      } else {
         throw new NotFoundException("Profile not found");
      }
   }
}
