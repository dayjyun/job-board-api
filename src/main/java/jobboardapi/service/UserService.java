package jobboardapi.service;


import jobboardapi.exceptions.NotFoundException;
import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
