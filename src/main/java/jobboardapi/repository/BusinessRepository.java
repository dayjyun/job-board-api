package jobboardapi.repository;

import jobboardapi.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByName(String name);

    Optional<Business> findBusinessByIdAndUserId(Long businessId, Long userId);
}
