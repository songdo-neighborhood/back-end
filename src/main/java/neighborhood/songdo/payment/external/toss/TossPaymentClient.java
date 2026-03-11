package neighborhood.songdo.payment.external.toss;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import neighborhood.songdo.payment.dto.PaymentConfirmRequest;
import neighborhood.songdo.payment.external.toss.dto.TossConfirmResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TossPaymentClient {

    private final RestClient restClient;
    private final TossPaymentProperties properties;

    public TossPaymentClient(RestClient.Builder builder, TossPaymentProperties properties) {
        this.properties = properties;
        this.restClient = builder
                .baseUrl(properties.baseUrl())
                .build();
    }

    public TossConfirmResponse confirm(PaymentConfirmRequest request) {
        return restClient.post()
                .uri("/v1/payments/confirm")
                .header(HttpHeaders.AUTHORIZATION, createAuthorizationHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(TossConfirmResponse.class);
    }

    private String createAuthorizationHeader() {
        String value = properties.secretKey() + ":";
        String encoded = Base64.getEncoder()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encoded;
    }
}
