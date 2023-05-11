package jobboardapi.repository;

import jobboardapi.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findByName(String name);
}
