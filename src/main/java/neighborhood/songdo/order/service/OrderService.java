package neighborhood.songdo.order.service;

import static neighborhood.songdo.common.exception.ErrorCode.RESERVATION_ENTITY_NOT_FOUND;
import static neighborhood.songdo.common.exception.ErrorCode.USER_ENTITY_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.dto.OrderCreateReqDto;
import neighborhood.songdo.order.dto.OrderResDto;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.payment.domain.Payment;
import neighborhood.songdo.payment.domain.PaymentMethod;
import neighborhood.songdo.payment.repository.PaymentRepository;
import neighborhood.songdo.reservation.repository.ReservationRepository;
import neighborhood.songdo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public OrderResDto createOrder(OrderCreateReqDto requestDto) {
        if (!reservationRepository.existsById(requestDto.reservationId())) {
            throw new CustomException(RESERVATION_ENTITY_NOT_FOUND);
        }
        if (!userRepository.existsById(requestDto.userId())) {
            throw new CustomException(USER_ENTITY_NOT_FOUND);
        }

        Order order = Order.createOrder(requestDto.reservationId(),
                requestDto.userId(),
                requestDto.amount());

        Order savedOrder = orderRepository.save(order);
        paymentRepository.save(
                Payment.createPayment(savedOrder.getId(), PaymentMethod.TOSS_PAY, order.getAmount())
        );

        return OrderResDto.from(savedOrder);
    }
}
