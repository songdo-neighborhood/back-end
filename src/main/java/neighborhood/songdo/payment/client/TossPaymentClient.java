package neighborhood.songdo.payment.client;

import neighborhood.songdo.payment.domain.Payment;
import neighborhood.songdo.payment.dto.TossPaymentReqDto;
import neighborhood.songdo.payment.dto.TossPaymentResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Component
public class TossPaymentClient {

    private final String secretKey;
    private final String baseUrl;

    private final RestClient restClient;

    public TossPaymentClient(
            @Value("${toss.payments.secret-key}") String secretKey,
            @Value("${toss.payments.base-url}") String baseUrl
    ) {
        this.secretKey = secretKey;
        this.baseUrl = baseUrl;
        this.restClient = RestClient.create();
    }

    @Transactional
    public void confirmPayment(Payment payment, TossPaymentReqDto request) {
        TossPaymentResDto response =
                restClient.post()
                        .uri(baseUrl + "/v1/payments/confirm")
                        .headers(headers -> headers.setBasicAuth(secretKey, ""))
                        .body(request)
                        .retrieve()
                        .body(TossPaymentResDto.class);

        payment.approve(response.paymentKey());
    }
}
