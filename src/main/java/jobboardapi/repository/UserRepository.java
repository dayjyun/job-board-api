package jobboardapi.repository;

import jobboardapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   User findUserByEmail(String email);
}
