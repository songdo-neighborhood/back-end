package neighborhood.songdo.restaurant.repository;

import java.util.Optional;
import neighborhood.songdo.restaurant.domain.OperatingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatingPolicyRepository extends JpaRepository<OperatingPolicy, Long> {

    Optional<OperatingPolicy> findByRestaurantId(Long restaurantId);
}
