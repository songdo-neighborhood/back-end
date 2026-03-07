package neighborhood.songdo.reservation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import neighborhood.songdo.reservation.domain.Reservation;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationThumbResDto {

    private Long id;

    private Long restaurantId;

    public static ReservationThumbResDto from(Reservation restaurant) {
        return new ReservationThumbResDto(
                restaurant.getId(),
                restaurant.getRestaurantId());
    }
}
