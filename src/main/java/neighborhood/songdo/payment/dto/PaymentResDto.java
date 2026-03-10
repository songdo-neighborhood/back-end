package neighborhood.songdo.payment.dto;

import neighborhood.songdo.payment.domain.PaymentStatus;

public record PaymentResDto(
        Long paymentId,
        Long orderId,
        Long reservationId,
        PaymentStatus status,
        Long amount
) {}

