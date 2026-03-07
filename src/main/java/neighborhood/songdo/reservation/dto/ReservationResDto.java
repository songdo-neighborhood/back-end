package neighborhood.songdo.reservation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import neighborhood.songdo.reservation.domain.Reservation;

import java.time.OffsetDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationResDto {

    private Long id;

    private Long userId;

    private Integer guestCount;

    private OffsetDateTime reservationDateTime;

    private Long reservationFee;

    private OffsetDateTime createdAt;

    public static ReservationResDto from(Reservation restaurant) {
        return new ReservationResDto(
                restaurant.getId(),
                restaurant.getUserId(),
                restaurant.getGuestCount(),
                restaurant.getReservationDateTime(),
                restaurant.getReservationFee(),
                restaurant.getCreatedAt());
    }
}
