package neighborhood.songdo.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;
import neighborhood.songdo.common.domain.BaseEntity;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public static Restaurant createRestaurant(
            String title,
            String address,
            String description
    ) {
        return Restaurant.builder()
                .title(title)
                .address(address)
                .description(description)
                .build();
    }

    public void update(
            String title,
            String address,
            String description
    ) {
        this.title = title;
        this.address = address;
        this.description = description;
    }
}
