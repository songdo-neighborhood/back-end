package neighborhood.songdo.payment.service;

import static neighborhood.songdo.common.exception.ErrorCode.ENTITY_NOT_FOUND;
import static neighborhood.songdo.common.exception.ErrorCode.ORDER_NOT_PAYABLE;
import static neighborhood.songdo.common.exception.ErrorCode.PAYMENT_ALREADY_APPROVED;
import static neighborhood.songdo.common.exception.ErrorCode.PAYMENT_AMOUNT_MISMATCH;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.payment.domain.Payment;
import neighborhood.songdo.payment.dto.PaymentConfirmRequest;
import neighborhood.songdo.payment.external.toss.TossPaymentClient;
import neighborhood.songdo.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final TossPaymentClient tossPaymentClient;

    @Transactional
    public void confirm(PaymentConfirmRequest request) {
        Order order = orderRepository.findByMerchantOrderId(request.orderId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        Payment payment = paymentRepository.findByOrderId(order.getId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        validateAlreadyApproved(payment);
        validateOrderState(order);
        validatePaymentAmount(request, payment);

        payment.markPending();
        order.markPaymentPending();

        tossPaymentClient.confirm(request);

        order.markPaid();
        payment.approve(request.paymentKey());
    }

    private static void validateAlreadyApproved(Payment payment) {
        if (payment.isApproved()) {
            throw new CustomException(PAYMENT_ALREADY_APPROVED);
        }
    }

    private static void validateOrderState(Order order) {
        if (!order.isPayable()) {
            throw new CustomException(ORDER_NOT_PAYABLE);
        }
    }

    private static void validatePaymentAmount(PaymentConfirmRequest request, Payment payment) {
        if (!payment.isEqualAmount(request.amount())) {
            throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
        }
    }
}
