package neighborhood.songdo.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class RestaurantUpdateReqDto {

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    @NotBlank
    private String description;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;
}
