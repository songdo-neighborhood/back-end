package neighborhood.songdo.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ReservationCreateReqDto {

    @NotNull
    private Long restaurantId;

    @NotNull
    private Long userId;

    @NotNull
    private Integer guestCount;

    @NotNull
    private OffsetDateTime reservationDateTime;

    @NotNull
    private Long reservationFee;
}
