package neighborhood.songdo.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.dto.CursorPage;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantCreateReqDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantResDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantThumbResDto;
import neighborhood.songdo.restaurant.dto.restaurant_dto.RestaurantUpdateReqDto;
import neighborhood.songdo.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResDto> getRestaurantById(
            @PathVariable Long id) {
        RestaurantResDto dto = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CursorPage<RestaurantThumbResDto>> getRestaurants(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<RestaurantThumbResDto> page = restaurantService.getRestaurants(cursor, size);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResDto> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantUpdateReqDto restaurantUpdateReqDto) {
        RestaurantResDto dto = restaurantService.updateRestaurant(id, restaurantUpdateReqDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(
            @PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
