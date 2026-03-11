package neighborhood.songdo.payment.external.toss;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "toss.payments")
public record TossPaymentProperties(
        String baseUrl,
        String secretKey
) {
}
