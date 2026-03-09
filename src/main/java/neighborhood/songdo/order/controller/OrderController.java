package neighborhood.songdo.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.order.dto.OrderCreateReqDto;
import neighborhood.songdo.order.dto.OrderResDto;
import neighborhood.songdo.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResDto> createOrder(
            @Valid @RequestBody OrderCreateReqDto createReqDto
    ) {
        OrderResDto dto = orderService.createOrder(createReqDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
