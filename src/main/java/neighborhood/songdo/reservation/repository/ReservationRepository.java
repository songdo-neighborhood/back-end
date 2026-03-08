package neighborhood.songdo.reservation.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import neighborhood.songdo.reservation.domain.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 최신순으로 조회
    List<Reservation> findAllByOrderByIdDesc(Pageable pageable);

    // cursor 이후 데이터를 조회
    List<Reservation> findByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reservation r " +
           "WHERE r.restaurantId = :restaurantId " +
           "AND r.reservationDateTime = :dateTime")
    Optional<Reservation> findByRestaurantAndDateTimeWithLock(
        @Param("restaurantId") Long restaurantId,
        @Param("dateTime") OffsetDateTime dateTime
    );
}
