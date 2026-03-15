package neighborhood.songdo.restaurant.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.dto.CursorPage;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.restaurant.domain.OperatingPolicy;
import neighborhood.songdo.restaurant.domain.Restaurant;
import neighborhood.songdo.restaurant.dto.operating_policy_dto.OperatingPolicyReqDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantCreateReqDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantResDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantThumbResDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantUpdateReqDto;
import neighborhood.songdo.restaurant.repository.OperatingPolicyRepository;
import neighborhood.songdo.restaurant.repository.RestaurantRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static neighborhood.songdo.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OperatingPolicyRepository operatingPolicyRepository;

    @Transactional
    public RestaurantResDto createRestaurant(RestaurantCreateReqDto dto) {
        Restaurant restaurant = Restaurant.createRestaurant(
                dto.getTitle(),
                dto.getAddress(),
                dto.getDescription()
        );

        restaurantRepository.save(restaurant);

        OperatingPolicyReqDto operatingPolicyReqDto = dto.getOperatingPolicyReqDto();
        OperatingPolicy policy = OperatingPolicy.createOperatingPolicy(
                restaurant.getId(),
                operatingPolicyReqDto.openTime(),
                operatingPolicyReqDto.closeTime(),
                operatingPolicyReqDto.breakStartTime(),
                operatingPolicyReqDto.breakEndTime(),
                operatingPolicyReqDto.slotUnit(),
                operatingPolicyReqDto.maxCapacitySlot()
        );
        operatingPolicyRepository.save(policy);

        return RestaurantResDto.from(restaurant, policy);
    }

    public RestaurantResDto getRestaurantById(Long id) {
        Restaurant findRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        OperatingPolicy operatingPolicy = operatingPolicyRepository.findByRestaurantId(findRestaurant.getId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        return RestaurantResDto.from(findRestaurant, operatingPolicy);
    }

    public CursorPage<RestaurantThumbResDto> getRestaurants(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);

        List<Restaurant> restaurants = cursor == null
                ? restaurantRepository.findAllByOrderByIdDesc(pageable)
                : restaurantRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);

        boolean hasNext = restaurants.size() > size;
        List<RestaurantThumbResDto> content = restaurants.stream()
                .limit(size)
                .map(RestaurantThumbResDto::from)
                .toList();

        String nextCursor = hasNext && !content.isEmpty()
                ? String.valueOf(content.getLast().getId())
                : null;

        return CursorPage.of(content, nextCursor, hasNext);
    }

    @Transactional
    public RestaurantResDto updateRestaurant(Long id, RestaurantUpdateReqDto dto) {
        Restaurant findRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        findRestaurant.update(dto.getTitle(),
                dto.getAddress(),
                dto.getDescription()
        );
        return RestaurantResDto.from(findRestaurant);
    }

    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
        operatingPolicyRepository.deleteByRestaurantId(id);
    }
}
