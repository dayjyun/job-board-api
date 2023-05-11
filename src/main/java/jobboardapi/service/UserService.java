package jobboardapi.service;


import jobboardapi.exceptions.AlreadyExistsException;
import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import jobboardapi.security.JWTUtils;
import jobboardapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository,
                       @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    public User createUser(User userObject) {
        if (!userRepository.existsByEmail(userObject.getEmail())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new AlreadyExistsException("user with email address " + userObject.getEmail() +
                    " already exists");
        }
    }


    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * getUserById retrieves the user by the user id, if the user id exists.
     * If the user id does not exist, we throw the NotFoundException
     * @param userId is what we're searching by
     * @return the optional of the user
     */
    public Optional<User> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user;
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
