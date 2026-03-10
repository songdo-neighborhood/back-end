package neighborhood.songdo.payment.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentReqDto(
        @NotNull
        String paymentKey,

        @NotNull
        Long orderId,

        @NotNull
        Long amount
) {
}
