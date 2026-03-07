package neighborhood.songdo.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ReservationUpdateReqDto {

    @NotNull
    private OffsetDateTime reservationDateTime;
}
