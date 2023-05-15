package jobboardapi.repository;

import jobboardapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   // register
   boolean existsByEmail(String email);

   // login
   User findUserByEmail(String email);
}
