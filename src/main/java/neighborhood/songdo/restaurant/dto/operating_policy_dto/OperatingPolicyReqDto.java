package neighborhood.songdo.restaurant.dto.operating_policy_dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;
import neighborhood.songdo.restaurant.domain.SlotUnit;

public record OperatingPolicyReqDto(
        @NotNull
        LocalTime openTime,

        @NotNull
        LocalTime closeTime,

        LocalTime breakStartTime,

        LocalTime breakEndTime,

        @NotNull
        SlotUnit slotUnit,

        @NotNull
        @Positive
        Integer maxCapacitySlot) {
}
