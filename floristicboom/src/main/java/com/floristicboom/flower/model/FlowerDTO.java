package com.floristicboom.flower.model;


import com.floristicboom.utils.Color;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record FlowerDTO(
        Long id,
        @Size(max = 45, message = "{name.size.max}")
        @NotBlank(message = "{name.notBlank}")
        @NotNull(message = "{name.notNull}")
        String name,
        @Size(max = 255, message = "{description.size.max}")
        @NotBlank(message = "{description.notBlank}")
        @NotNull(message = "{description.notNull}")
        String description,
        @Positive(message = "{price.positive}")
        @NotNull(message = "{price.notNull}")
        BigDecimal price,
        @NotNull(message = "{color.notNull}")
        Color color,
        @PositiveOrZero(message = "{availableQuantity.positiveOrZero}")
        @NotNull(message = "{availableQuantity.notNull}")
        Integer availableQuantity,
        String imageUri) {
}