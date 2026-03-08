package neighborhood.songdo.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.dto.CursorPage;
import neighborhood.songdo.reservation.dto.ReservationCreateReqDto;
import neighborhood.songdo.reservation.dto.ReservationResDto;
import neighborhood.songdo.reservation.dto.ReservationThumbResDto;
import neighborhood.songdo.reservation.dto.ReservationUpdateReqDto;
import neighborhood.songdo.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResDto> createRestaurant(
            @Valid @RequestBody ReservationCreateReqDto reservationCreateReqDto) {
        ReservationResDto dto = reservationService.createReservation(reservationCreateReqDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResDto> getReservationById(
            @PathVariable Long id) {
        ReservationResDto dto = reservationService.getReservationById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CursorPage<ReservationThumbResDto>> getReservations(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size) {
        CursorPage<ReservationThumbResDto> page = reservationService.getReservations(cursor, size);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResDto> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationUpdateReqDto reservationUpdateReqDto) {
        ReservationResDto dto = reservationService.updateReservation(id, reservationUpdateReqDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
