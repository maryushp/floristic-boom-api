package com.floristicboom.bonus.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BonusDTO (
    Long id,
    @Size(max = 45, message = "{promoCode.size.max}")
    @NotNull(message = "{promoCode.notNull}")
    String promoCode,
    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.notNull}")
    BigDecimal discount,
    @NotNull(message = "{durationDate.notNull}")
    LocalDateTime durationDate) {
}