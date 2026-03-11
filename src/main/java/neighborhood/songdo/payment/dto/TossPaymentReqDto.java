package neighborhood.songdo.payment.dto;


public record TossPaymentReqDto(
        String paymentKey,
        String orderId,
        Long amount
) {
    public static TossPaymentReqDto from(String paymentKey, String orderId, Long amount) {
        return new TossPaymentReqDto(paymentKey, orderId, amount);
    }
}
