package neighborhood.songdo.restaurant.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Restaurant {

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

    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    public static Restaurant createRestaurant(
            String title,
            String address,
            String description,
            OffsetDateTime startTime,
            OffsetDateTime endTime
    ) {
        return Restaurant.builder()
                .title(title)
                .address(address)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public void update(
            String title,
            String address,
            String description,
            OffsetDateTime startTime,
            OffsetDateTime endTime
    ) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
