package jobboardapi.seed;

import jobboardapi.models.User;
import jobboardapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception{
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User(1L, "DeShe", "deshe@gmail.com", "pw", "resume1");
            User user2 = new User(2L, "Kevin", "kevin@gmail.com", "pw", "resume2");
            User user3 = new User(3L, "Kim", "kim@gmail.com", "pw", "resume3");
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }
    }
}
