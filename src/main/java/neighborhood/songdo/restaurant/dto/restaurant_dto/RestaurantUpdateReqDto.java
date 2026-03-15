package neighborhood.songdo.restaurant.dto.restaurant_dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import neighborhood.songdo.restaurant.dto.operating_policy_dto.OperatingPolicyReqDto;

@Getter
public class RestaurantUpdateReqDto {

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    @NotBlank
    private String description;

    @Valid
    @NotNull
    private OperatingPolicyReqDto operatingPolicyReqDto;
}
