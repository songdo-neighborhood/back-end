package neighborhood.songdo.restaurant.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import neighborhood.songdo.restaurant.domain.Restaurant;

import java.time.OffsetDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RestaurantResDto {

    private Long id;

    private String title;

    private String address;

    private String description;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    public static RestaurantResDto from(Restaurant restaurant) {
        return new RestaurantResDto(
                restaurant.getId(),
                restaurant.getTitle(),
                restaurant.getAddress(),
                restaurant.getDescription(),
                restaurant.getStartTime(),
                restaurant.getEndTime());
    }
}
