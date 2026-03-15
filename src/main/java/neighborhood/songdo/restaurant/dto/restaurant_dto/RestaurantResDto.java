package neighborhood.songdo.restaurant.dto.restaurant_dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import neighborhood.songdo.restaurant.domain.OperatingPolicy;
import neighborhood.songdo.restaurant.domain.Restaurant;
import neighborhood.songdo.restaurant.dto.operating_policy_dto.OperatingPolicyResDto;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RestaurantResDto {

    private Long id;

    private String title;

    private String address;

    private String description;

    private OperatingPolicyResDto operatingPolicyResDto;

    public static RestaurantResDto from(Restaurant restaurant, OperatingPolicy operatingPolicy) {
        return new RestaurantResDto(
                restaurant.getId(),
                restaurant.getTitle(),
                restaurant.getAddress(),
                restaurant.getDescription(),
                OperatingPolicyResDto.from(operatingPolicy));
    }
}
