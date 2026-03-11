package neighborhood.songdo.order.repository;

import java.util.Optional;
import neighborhood.songdo.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByMerchantOrderId(String id);
}
