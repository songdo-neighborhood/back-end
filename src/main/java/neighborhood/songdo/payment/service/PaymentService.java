package neighborhood.songdo.payment.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.common.exception.ErrorCode;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.payment.dto.PaymentReqDto;
import neighborhood.songdo.payment.dto.PaymentResDto;
import neighborhood.songdo.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResDto confirmPayment(PaymentReqDto reqDto) {
        if (!orderRepository.existsById(reqDto.orderId())) {
            throw new CustomException(ErrorCode.ORDER_ENTITY_NOT_FOUND);
        }

        if (!paymentRepository.existsByOrderId(reqDto.orderId())) {
            throw new CustomException(ErrorCode.PAYMENT_ENTITY_NOT_FOUND);
        }




        return null;
    }
}
