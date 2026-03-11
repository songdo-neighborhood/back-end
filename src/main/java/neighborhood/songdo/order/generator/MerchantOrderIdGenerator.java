package neighborhood.songdo.order.generator;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MerchantOrderIdGenerator {

    public String generate() {
        return "ORDER_" + UUID.randomUUID().toString();
    }
}
