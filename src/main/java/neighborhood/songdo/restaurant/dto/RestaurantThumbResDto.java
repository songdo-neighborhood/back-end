package neighborhood.songdo.restaurant.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import neighborhood.songdo.restaurant.domain.Restaurant;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RestaurantThumbResDto {

    private Long id;

    private String title;

    public static RestaurantThumbResDto from(Restaurant restaurant) {
        return new RestaurantThumbResDto(
                restaurant.getId(),
                restaurant.getTitle());
    }
}
