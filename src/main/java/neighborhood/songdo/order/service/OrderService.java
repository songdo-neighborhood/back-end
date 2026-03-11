package neighborhood.songdo.order.service;

import static neighborhood.songdo.common.exception.ErrorCode.RESERVATION_ENTITY_NOT_FOUND;
import static neighborhood.songdo.common.exception.ErrorCode.USER_ENTITY_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.dto.OrderCreateReqDto;
import neighborhood.songdo.order.dto.OrderResDto;
import neighborhood.songdo.order.generator.MerchantOrderIdGenerator;
import neighborhood.songdo.order.repository.OrderRepository;
import neighborhood.songdo.reservation.repository.ReservationRepository;
import neighborhood.songdo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final MerchantOrderIdGenerator merchantIdGenerator;

    public OrderResDto createOrder(OrderCreateReqDto requestDto) {
        if (!reservationRepository.existsById(requestDto.reservationId())) {
            throw new CustomException(RESERVATION_ENTITY_NOT_FOUND);
        }
        if (!userRepository.existsById(requestDto.userId())) {
            throw new CustomException(USER_ENTITY_NOT_FOUND);
        }

        Order order = Order.createOrder(requestDto.reservationId(),
                requestDto.userId(),
                requestDto.amount(),
                merchantIdGenerator.generate()
        );

        Order savedOrder = orderRepository.save(order);

        return OrderResDto.from(savedOrder);
    }
}
