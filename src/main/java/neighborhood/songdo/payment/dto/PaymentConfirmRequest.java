package neighborhood.songdo.payment.dto;

public record PaymentConfirmRequest(String paymentKey,
                                    String orderId,
                                    Long amount) {
}
