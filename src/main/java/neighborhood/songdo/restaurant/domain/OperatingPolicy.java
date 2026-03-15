package neighborhood.songdo.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import neighborhood.songdo.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OperatingPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_policy_id")
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "break_start_time")
    private LocalTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalTime breakEndTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "slot_unit", nullable = false)
    private SlotUnit slotUnit;

    @Column(name = "max_capacity_per_slot", nullable = false)
    private Integer maxCapacityPerSlot;

    private OperatingPolicy(
            Long restaurantId,
            LocalTime openTime,
            LocalTime closeTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            SlotUnit slotUnit,
            Integer maxCapacityPerSlot
    ) {
        this.restaurantId = restaurantId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.slotUnit = slotUnit;
        this.maxCapacityPerSlot = maxCapacityPerSlot;
    }

    public static OperatingPolicy createOperatingPolicy(
            Long restaurantId,
            LocalTime openTime,
            LocalTime closeTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            SlotUnit slotUnit,
            Integer maxCapacityPerSlot
    ) {
        return new OperatingPolicy(
                restaurantId,
                openTime,
                closeTime,
                breakStartTime,
                breakEndTime,
                slotUnit,
                maxCapacityPerSlot
        );
    }

    public void update(
            LocalTime openTime,
            LocalTime closeTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime,
            SlotUnit slotUnit,
            Integer maxCapacityPerSlot
    ) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.slotUnit = slotUnit;
        this.maxCapacityPerSlot = maxCapacityPerSlot;
    }
}
