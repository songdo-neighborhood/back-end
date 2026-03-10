package neighborhood.songdo.payment.service;

import static neighborhood.songdo.common.exception.ErrorCode.ENTITY_NOT_FOUND;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.payment.dto.PaymentConfirmRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;

    public void confirm(PaymentConfirmRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        tossPaymentClient.confirm(request);

        order.confirm();
    }
}
