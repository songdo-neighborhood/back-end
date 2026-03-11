package neighborhood.songdo.payment.external.toss.dto;

import java.time.OffsetDateTime;

public record TossConfirmResponse(
        String paymentKey,
        Long orderId,
        String status,
        Long totalAmount,
        String method,
        OffsetDateTime approvedAt
) {
}
