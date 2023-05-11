package jobboardapi.seed;

import jobboardapi.models.Business;
import jobboardapi.models.Job;
import jobboardapi.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessDataLoader implements CommandLineRunner {

    @Autowired
    BusinessRepository businessRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBusinessData();
    }

    private void loadBusinessData() {

        if (businessRepository.count() == 0) {
            Business business1 = new Business(1L, "DeShe's Business", "Michigan");
            Business business2 = new Business(2L, "Kevin's Business", "Illinois");
            Business business3 = new Business(3L, "Kim's Business", "Illinois");
            businessRepository.save(business1);
            businessRepository.save(business2);
            businessRepository.save(business3);
        }
    }
}
