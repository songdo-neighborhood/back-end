package neighborhood.songdo.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderCreateReqDto(
        @NotNull
        Long reservationId,
        @NotNull
        Long userId,
        @NotNull
        @Min(0)
        @Max(1_000_000_000)
        Long amount
) {
}
