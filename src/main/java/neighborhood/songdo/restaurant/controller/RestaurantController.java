package neighborhood.songdo.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.restaurant.dto.RestaurantCreateReqDto;
import neighborhood.songdo.restaurant.dto.RestaurantResDto;
import neighborhood.songdo.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResDto> createRestaurant(
            @Valid @RequestBody RestaurantCreateReqDto restaurantCreateReqDto) {
        RestaurantResDto dto = restaurantService.createRestaurant(restaurantCreateReqDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
