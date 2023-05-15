package jobboardapi.service;

import jobboardapi.exceptions.BadRequestException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.exceptions.UnauthorizedException;
import jobboardapi.models.Job;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.MyUserDetails;
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

   // Purpose is to encrypt password when updating profile
   public MyProfileService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
      this.passwordEncoder = passwordEncoder;
      this.userRepository = userRepository;
   }

   /**
    * Retrieves currently logged-in user's data. If there is no data (Example: After account deletion), an Unauthorized error message is
    * thrown.
    *
    * @return Logged-in user's data
    */
   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   public static User getLoggedInUser() {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      // Check that there is a logged-in user
      if (userDetails.getUser() == null || userDetails.getUsername().isEmpty() || userDetails.getUsername() == null) {
         // Return an error if the user is not found
         throw new UnauthorizedException("Unauthorized");
      }
      // Return the data for the logged-in user
      return userDetails.getUser();
   }

   /**
    * updateMyProfile allows a logged-in user to update their information. Updates but you need to generate a new JWT
    *
    * @param updatedBody the incoming user data requesting to be updated
    * @return the logged-in user's data after the update
    */
   public User updateMyProfile(User updatedBody) {
      // Obtain the ID for the logged-in user
      Optional<User> user = userRepository.findById(getLoggedInUser().getId());
      // Check there is data for the logged-in user
      if (user.isPresent()) {
         // check that the email does not already in the database, and it's not the same as current email
         if (userRepository.existsByEmail(updatedBody.getEmail()) && !user.get().getEmail().equals(updatedBody.getEmail())) {
            throw new BadRequestException("Email already in use");
         }
         // Check that the password field is not empty when updating the password
         if (updatedBody.getPassword() != null && !updatedBody.getPassword().isEmpty()) {
            user.get().setPassword(passwordEncoder.encode(updatedBody.getPassword()));
         }
         // Check that the name field is not empty when updating the name
         if (updatedBody.getName() != null && !updatedBody.getName().isEmpty()) {
            user.get().setName(updatedBody.getName());
         }
         // Check that the email field is not empty when updating the email
         if (updatedBody.getEmail() != null && !updatedBody.getEmail().isEmpty()) {
            user.get().setEmail(updatedBody.getEmail());
         }
         // Check that the resume field is not null
         // If the value of the resume is an empty string, it will delete the resume from the profile
         if (updatedBody.getResume() != null) {
            user.get().setResume(updatedBody.getResume());
         }
         // Save the updated data for the logged-in user
         return userRepository.save(user.get());
      } else {
         return null;
      }
   }

   /**
    * deleteMyProfile deletes the current logged-in user's profile. If the profile is not found (which would be strange) then a
    * NotFoundException is thrown
    *
    * @return the deleted data for the logged-in user
    */
   public User deleteMyProfile() {
      // Obtain the ID for the logged-in user
      Optional<User> myProfile = userRepository.findById(getLoggedInUser().getId());
      // Check there is data for the logged-in user
      if (myProfile.isPresent()) {
         // Remove the logged-in user's data from the database by its ID
         userRepository.deleteById(getLoggedInUser().getId());
         // Return the information of the deleted user
         return myProfile.get();
      } else {
         // Throw an error if there is no logged-in user, when you're logged-in...
         throw new NotFoundException("Odd. That wasn't supposed to happen.");
      }
   }


   /**
    * getJobsIAppliedFor returns a list of jobs the logged-in user has applied for. If no jobs are found, a NotFoundException is thrown. If
    * the profile is not found, a NotFoundException is thrown as well.
    *
    * @return a list of user objects that have applied to the job listing.
    */
   public List<Job> getJobsIAppliedFor() {
      // Obtain the ID for the logged-in user
      Optional<User> myProfile = userRepository.findById(getLoggedInUser().getId());
      // Check there is data for the logged-in user
      if (myProfile.isPresent()) {
         // Obtain the list of jobs the logged-in user has applied for
         List<Job> listOfJobsIAppliedTo = myProfile.get().getListOfJobsAppliedTo();
         // Check that the list of jobs applied to has at least one job available
         if (listOfJobsIAppliedTo.size() > 0) {
            // Return the list of jobs the logged-in user has applied to
            return listOfJobsIAppliedTo;
         } else {
            // Throw an error if the list of the logged-in user has applied to is empty
            throw new NotFoundException("No jobs applied to");
         }
      } else {
         // Throw an error if there is no logged-in user, when you're logged-in...
         throw new NotFoundException("Profile not found");
      }
   }
}
