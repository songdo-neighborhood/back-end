package neighborhood.songdo.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.payment.dto.PaymentReqDto;
import neighborhood.songdo.payment.dto.PaymentResDto;
import neighborhood.songdo.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/payments/")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<PaymentResDto> confirmPayment(@Valid @RequestBody PaymentReqDto reqDto) {
        PaymentResDto resDto = paymentService.confirmPayment(reqDto);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
}
