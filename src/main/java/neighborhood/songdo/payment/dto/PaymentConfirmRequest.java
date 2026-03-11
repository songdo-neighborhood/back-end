package neighborhood.songdo.payment.dto;

public record PaymentConfirmRequest(String paymentKey,
                                    String merchantOrderId,
                                    Long amount) {
}
