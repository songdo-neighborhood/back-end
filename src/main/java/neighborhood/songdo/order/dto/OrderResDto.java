package neighborhood.songdo.order.dto;

import neighborhood.songdo.order.domain.Order;
import neighborhood.songdo.order.domain.OrderStatus;

public record OrderResDto(
        Long id,
        String tossOrderId,
        Long reservationId,
        Long userId,
        Long amount,
        OrderStatus orderStatus

) {
    public static OrderResDto from(Order order) {
        return new OrderResDto(
                order.getId(),
                order.getTossOrderId(),
                order.getReservationId(),
                order.getUserId(),
                order.getAmount(),
                order.getOrderStatus()
        );
    }
}
