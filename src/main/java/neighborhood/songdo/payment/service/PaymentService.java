package neighborhood.songdo.payment.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.common.exception.ErrorCode;
import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.payment.client.TossPaymentClient;
import neighborhood.songdo.payment.domain.Payment;
import neighborhood.songdo.payment.dto.PaymentReqDto;
import neighborhood.songdo.payment.dto.PaymentResDto;
import neighborhood.songdo.payment.dto.TossPaymentReqDto;
import neighborhood.songdo.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final TossPaymentClient tossPaymentClient;

    @Transactional
    public PaymentResDto confirmPayment(PaymentReqDto reqDto) {
        Order findOrder = orderRepository.findByTossOrderId(reqDto.tossOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_ENTITY_NOT_FOUND));

        Payment findPayment = paymentRepository.findByOrderId(findOrder.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_ENTITY_NOT_FOUND));

        if (!reqDto.amount().equals(findOrder.getAmount())) {
            throw new CustomException(ErrorCode.AMOUNT_MISMATCH);
        }

        tossPaymentClient.confirmPayment(findPayment,
                TossPaymentReqDto.from(reqDto.paymentKey(), reqDto.tossOrderId(), reqDto.amount()));

        return PaymentResDto.from(findPayment);
    }
}
