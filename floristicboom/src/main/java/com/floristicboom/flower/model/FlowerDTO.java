package com.floristicboom.flower.model;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.math.BigDecimal;

public record FlowerDTO(
        Long id,
        @Size(max = 45, message = "{name.size.max}")
        @NotBlank(message = "{name.notBlank}")
        String name,
        @Size(max = 255, message = "{description.size.max}")
        @NotBlank(message = "{description.notBlank}")
        String description,
        @Positive(message = "{price.positive}")
        BigDecimal price,
        Color color,
        @PositiveOrZero(message = "{availableQuantity.positiveOrZero}")
        Integer availableQuantity,
        String imageUri) {
}