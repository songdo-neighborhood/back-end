package neighborhood.songdo.reservation.service;

import lombok.RequiredArgsConstructor;
import neighborhood.songdo.common.dto.CursorPage;
import neighborhood.songdo.common.exception.CustomException;
import neighborhood.songdo.reservation.repository.ReservationRepository;
import neighborhood.songdo.reservation.domain.Reservation;
import neighborhood.songdo.reservation.dto.ReservationCreateReqDto;
import neighborhood.songdo.reservation.dto.ReservationResDto;
import neighborhood.songdo.reservation.dto.ReservationThumbResDto;
import neighborhood.songdo.reservation.dto.ReservationUpdateReqDto;
import neighborhood.songdo.restaurant.repository.RestaurantRepository;
import neighborhood.songdo.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static neighborhood.songdo.common.exception.ErrorCode.ALREADY_RESERVATION;
import static neighborhood.songdo.common.exception.ErrorCode.ENTITY_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationResDto createReservation(ReservationCreateReqDto dto) {
        if (!restaurantRepository.existsById(dto.getRestaurantId()) || !userRepository.existsById(dto.getUserId())) {
            throw new CustomException(ENTITY_NOT_FOUND);
        }

        if (reservationRepository.findByRestaurantAndDateTimeWithLock(
                dto.getRestaurantId(),
                dto.getReservationDateTime()
        ).isPresent()) {
            throw new CustomException(ALREADY_RESERVATION);
        }

        Reservation reservation = Reservation.createReservation(
                dto.getRestaurantId(),
                dto.getUserId(),
                dto.getGuestCount(),
                dto.getReservationDateTime(),
                dto.getReservationFee()
        );

        reservationRepository.save(reservation);
        return ReservationResDto.from(reservation);
    }

    public ReservationResDto getReservationById(Long id) {
        Reservation findReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));
        return ReservationResDto.from(findReservation);
    }

    public CursorPage<ReservationThumbResDto> getReservations(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);

        List<Reservation> reservations = cursor == null
                ? reservationRepository.findAllByOrderByIdDesc(pageable)
                : reservationRepository.findByIdLessThanOrderByIdDesc(cursor, pageable);

        boolean hasNext = reservations.size() > size;
        List<ReservationThumbResDto> content = reservations.stream()
                .limit(size)
                .map(ReservationThumbResDto::from)
                .toList();

        String nextCursor = hasNext && !content.isEmpty()
                ? String.valueOf(content.getLast().getId())
                : null;

        return CursorPage.of(content, nextCursor, hasNext);
    }

    @Transactional
    public ReservationResDto updateReservation(Long id, ReservationUpdateReqDto dto) {
        Reservation findReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND));

        findReservation.updateReservationDateTime(
                dto.getReservationDateTime()
        );
        return ReservationResDto.from(findReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
