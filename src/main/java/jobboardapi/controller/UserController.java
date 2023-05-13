package jobboardapi.controller;

import jobboardapi.models.User;
import jobboardapi.models.login.LoginRequest;
import jobboardapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Functionality: User creates account
    // http://localhost:8080/auth/users/register
    @PostMapping("/register")
    public User createUser(@RequestBody @Valid User userObject) {
        return userService.createUser(userObject);
    }

    // Functionality: User logs into account
    // http://localhost:8080/auth/users/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    // Functionality: Returns user account details
    // http://localhost:8080/api/users/{userId}
    @GetMapping(path = "/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}
