package neighborhood.songdo.payment.dto;

import neighborhood.songdo.payment.domain.Payment;
import neighborhood.songdo.payment.domain.PaymentStatus;

public record PaymentResDto(
        Long paymentId,
        Long orderId,
        PaymentStatus status,
        Long amount
) {
    public static PaymentResDto from(Payment findPayment) {
        return new PaymentResDto(
                findPayment.getId(),
                findPayment.getOrderId(),
                findPayment.getPaymentStatus(),
                findPayment.getAmount()
        );
    }
}
