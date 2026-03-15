package neighborhood.songdo.restaurant.dto.operating_policy_dto;

import java.time.LocalTime;
import neighborhood.songdo.restaurant.domain.OperatingPolicy;
import neighborhood.songdo.restaurant.domain.SlotUnit;

public record OperatingPolicyResDto(
        Long id,

        Long restaurantId,

        LocalTime openTime,

        LocalTime closeTime,

        LocalTime breakStartTime,

        LocalTime breakEndTime,

        SlotUnit slotUnit,

        Integer maxCapacitySlot
) {
    public static OperatingPolicyResDto from(OperatingPolicy operatingPolicy) {
        return new OperatingPolicyResDto(
                operatingPolicy.getId(),
                operatingPolicy.getRestaurantId(),
                operatingPolicy.getOpenTime(),
                operatingPolicy.getCloseTime(),
                operatingPolicy.getBreakStartTime(),
                operatingPolicy.getBreakEndTime(),
                operatingPolicy.getSlotUnit(),
                operatingPolicy.getMaxCapacityPerSlot()
        );
    }
}
