package neighborhood.songdo.restaurant.repository;

import neighborhood.songdo.restaurant.domain.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 최신순으로 조회
    List<Restaurant> findAllByOrderByIdDesc(Pageable pageable);

    // cursor 이후 데이터를 조회
    List<Restaurant> findByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);
}
