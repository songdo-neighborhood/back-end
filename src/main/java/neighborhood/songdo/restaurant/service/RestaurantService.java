package neighborhood.songdo.restaurant.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.restaurant.domain.Restaurant;
import neighborhood.songdo.restaurant.dto.RestaurantCreateReqDto;
import neighborhood.songdo.restaurant.dto.RestaurantResDto;
import neighborhood.songdo.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResDto createRestaurant(RestaurantCreateReqDto dto) {
        Restaurant restaurant = Restaurant.createRestaurant(
                dto.getTitle(),
                dto.getAddress(),
                dto.getDescription(),
                dto.getStartTime(),
                dto.getEndTime()
        );

        restaurantRepository.save(restaurant);
        return RestaurantResDto.from(restaurant);
    }
}
