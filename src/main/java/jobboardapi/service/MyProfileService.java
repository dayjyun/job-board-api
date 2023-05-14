package jobboardapi.service;

import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MyProfileService {

   private UserRepository userRepository;

   public static User getLoggedInUser() {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return userDetails.getUser();
   }

}
