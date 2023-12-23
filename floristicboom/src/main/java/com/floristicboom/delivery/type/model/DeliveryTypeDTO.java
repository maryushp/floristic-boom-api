package com.floristicboom.delivery.type.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DeliveryTypeDTO (
    Long id,
    @Size(max = 45, message = "{name.size.max}")
    @NotBlank(message = "{name.notBlank}")
    @NotNull(message = "{name.notNull}")
    String name,
    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.notNull}")
    BigDecimal price) {
}