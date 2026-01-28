package neighborhood.songdo.like.repository;

import neighborhood.songdo.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByRestaurantId(Long restaurantId);

    void deleteByRestaurantId(Long restaurantId);
}
