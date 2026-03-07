package neighborhood.songdo.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import neighborhood.songdo.common.domain.BaseEntity;

import java.time.OffsetDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private Long restaurantId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "guest_count", nullable = false)
    private Integer guestCount;

    @Column(name = "reservation_date_time", nullable = false)
    private OffsetDateTime reservationDateTime;

    @Column(name = "reservation_fee", nullable = false)
    private Long reservationFee;

    public static Reservation createReservation(
            Long restaurantId,
            Long userId,
            Integer guestCount,
            OffsetDateTime reservationDateTime,
            Long reservationFee
    ) {
        return Reservation.builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .guestCount(guestCount)
                .reservationDateTime(reservationDateTime)
                .reservationFee(reservationFee)
                .build();
    }

    public void updateReservationDateTime(
            OffsetDateTime reservationDateTime
    ) {
        this.reservationDateTime = reservationDateTime;
    }
}
