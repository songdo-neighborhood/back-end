package neighborhood.songdo.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TossPaymentResDto(
        String paymentKey,
        String orderId,
        String status,
        Long totalAmount,
        String approvedAt
) {
}
