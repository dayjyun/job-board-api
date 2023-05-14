package jobboardapi.service;

import jobboardapi.exceptions.BadRequestException;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
         if(userRepository.existsByEmail(updatedBody.getEmail())  && !user.get().getEmail().equals(updatedBody.getEmail())) {
            throw new BadRequestException("Email already in use");
         }
         user.get().setName(updatedBody.getName());
         user.get().setEmail(updatedBody.getEmail());
         user.get().setPassword(passwordEncoder.encode(updatedBody.getPassword()));
         user.get().setResume(updatedBody.getResume());
         return userRepository.save(user.get());
      } else {
         return null;
      }
   }


}
