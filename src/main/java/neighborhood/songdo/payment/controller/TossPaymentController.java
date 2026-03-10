package neighborhood.songdo.payment.controller;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.payment.dto.PaymentConfirmRequest;
import neighborhood.songdo.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TossPaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments/confirm")
    public ResponseEntity<?> confirm(@RequestBody PaymentConfirmRequest request) {
        paymentService.confirm(request);
        return ResponseEntity.ok().build();
    }
}
