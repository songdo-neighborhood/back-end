package neighborhood.songdo.payment.domain;

import static neighborhood.songdo.common.exception.ErrorCode.INVALID_PAYMENT_STATUS_TRANSITION;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import neighborhood.songdo.common.domain.BaseEntity;
import neighborhood.songdo.common.exception.CustomException;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, updatable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_key", unique = true)
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "amount", nullable = false, updatable = false)
    private Long amount;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;

    @Column(name = "refunded_at")
    private OffsetDateTime refundedAt;

    public static Payment createPayment(
            Long orderId,
            PaymentMethod paymentMethod,
            Long amount
    ) {
        return Payment.builder()
                .orderId(orderId)
                .paymentMethod(paymentMethod)
                .paymentStatus(PaymentStatus.READY)
                .amount(amount)
                .build();
    }

    public void markPending() {
        validatePendingTransition();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public void approve(String paymentKey) {
        validateApproveTransition();
        this.paymentKey = paymentKey;
        this.approvedAt = OffsetDateTime.now();
        this.failureReason = null;
        this.paymentStatus = PaymentStatus.APPROVED;
    }

    public void fail(String failureReason) {
        validateFailTransition();
        this.failureReason = failureReason;
        this.paymentStatus = PaymentStatus.FAILED;
    }

    public void refund() {
        validateRefundTransition();
        this.refundedAt = OffsetDateTime.now();
        this.paymentStatus = PaymentStatus.REFUNDED;
    }

    public boolean isApproved() {
        return paymentStatus == PaymentStatus.APPROVED;
    }

    public boolean isEqualAmount(Long amount) {
        return this.amount.equals(amount);
    }

    private void validatePendingTransition() {
        if (paymentStatus != PaymentStatus.READY) {
            throw new CustomException(INVALID_PAYMENT_STATUS_TRANSITION);
        }
    }

    private void validateApproveTransition() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new CustomException(INVALID_PAYMENT_STATUS_TRANSITION);
        }
    }

    private void validateFailTransition() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new CustomException(INVALID_PAYMENT_STATUS_TRANSITION);
        }
    }

    private void validateRefundTransition() {
        if (paymentStatus != PaymentStatus.APPROVED) {
            throw new CustomException(INVALID_PAYMENT_STATUS_TRANSITION);
        }
    }
}
