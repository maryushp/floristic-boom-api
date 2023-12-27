package com.floristicboom.bouquet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BouquetPosition(
        @NotNull(message = "{flower.notNull}")
        Long flowerId,
        @NotNull(message = "{quantity.notNull")
        @Positive(message = "{quantity.positive}")
        Integer quantity) {
}