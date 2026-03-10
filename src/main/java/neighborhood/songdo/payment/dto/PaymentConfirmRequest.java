package neighborhood.songdo.payment.dto;

public record PaymentConfirmRequest(String paymentKey,
                                    Long orderId,
                                    Long amount) {
}
