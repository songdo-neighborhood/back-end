package neighborhood.songdo.order.domain;

import static neighborhood.songdo.common.exception.ErrorCode.INVALID_ORDER_STATUS_TRANSITION;
import static neighborhood.songdo.common.exception.ErrorCode.ORDER_AMOUNT_UPDATE_NOT_ALLOWED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import neighborhood.songdo.common.domain.BaseEntity;
import neighborhood.songdo.common.exception.CustomException;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "reservation_id", nullable = false, updatable = false)
    private Long reservationId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    public static Order createOrder(
           Long reservationId,
           Long userId,
           Long amount
    ) {
        return Order.builder()
                .reservationId(reservationId)
                .userId(userId)
                .amount(amount)
                .orderStatus(OrderStatus.CREATED)
                .build();
    }

    public void changeAmount(Long amount) {
        if (!isAmountChangeable()) {
            throw new CustomException(ORDER_AMOUNT_UPDATE_NOT_ALLOWED);
        }
        this.amount = amount;
    }

    public void markPaymentPending() {
        validatePaymentPendingTransition();
        this.orderStatus = OrderStatus.PAYMENT_PENDING;
    }

    public void markPaid() {
        validatePaidTransition();
        this.orderStatus = OrderStatus.PAID;
    }

    public void cancel() {
        validateCancelTransition();
        this.orderStatus = OrderStatus.CANCELED;
    }

    private boolean isAmountChangeable() {
        return orderStatus == OrderStatus.CREATED;
    }

    private void validatePaymentPendingTransition() {
        if (orderStatus != OrderStatus.CREATED) {
            throw new CustomException(INVALID_ORDER_STATUS_TRANSITION);
        }
    }

    private void validatePaidTransition() {
        if (orderStatus != OrderStatus.PAYMENT_PENDING) {
            throw new CustomException(INVALID_ORDER_STATUS_TRANSITION);
        }
    }

    private void validateCancelTransition() {
        if (orderStatus == OrderStatus.CANCELED) {
            throw new CustomException(INVALID_ORDER_STATUS_TRANSITION);
        }
    }
}
