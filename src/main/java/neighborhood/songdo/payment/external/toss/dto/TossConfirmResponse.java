package neighborhood.songdo.payment.external.toss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TossConfirmResponse(
        String paymentKey,
        String orderId,
        String status,
        Long totalAmount,
        String method,
        OffsetDateTime approvedAt
) {
}
