package jobboardapi.service;

import jobboardapi.exceptions.BadRequestException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.JWTRequestFilter;
import jobboardapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MyProfileService {

   Logger logger = Logger.getLogger((JWTRequestFilter.class.getName()));

   @Autowired
   private UserRepository userRepository;

   private final PasswordEncoder passwordEncoder;

   public MyProfileService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
      this.passwordEncoder = passwordEncoder;
      this.userRepository = userRepository;
   }

   public static User getLoggedInUser() {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return userDetails.getUser();
   }

   /**
    * Updates but you need to generate a new JWT
    * @param updatedBody
    * @return
    */
   public User updateMyProfile(User updatedBody) {
      Optional<User> user = userRepository.findById(getLoggedInUser().getId());
      if(user.isPresent()) {
         // check that the email does not already in the database, and it's not the same as current email
         if(userRepository.existsByEmail(updatedBody.getEmail()) && !user.get().getEmail().equals(updatedBody.getEmail())) {
            throw new BadRequestException("Email already in use");
         }
         if(updatedBody.getPassword() != null && !updatedBody.getPassword().isEmpty()) {
            user.get().setPassword(passwordEncoder.encode(updatedBody.getPassword()));
         }
         if(updatedBody.getPassword() != null && !updatedBody.getName().isEmpty()) {
            user.get().setName(updatedBody.getName());
         }
         if(updatedBody.getEmail() != null && !updatedBody.getEmail().isEmpty()) {
            user.get().setEmail(updatedBody.getEmail());
         }
         user.get().setResume(updatedBody.getResume());


            return userRepository.save(user.get());

      } else {
         return null;
      }
   }


   public Optional<User> deleteMyProfile(){
      Optional<User> myProfile = userRepository.findById(getLoggedInUser().getId());
      if(myProfile.isPresent()) {
         userRepository.deleteById(getLoggedInUser().getId());
         return myProfile;
      } else {
         throw new NotFoundException("Odd. That wasn't supposed to happen.");
      }
   }
}
