package com.floristicboom.bouquet.model;

import com.floristicboom.utils.Color;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BouquetCreationRequest(
        @NotBlank(message = "{name.notBlank}")
        @Size(max = 45, message = "{name.size.max}")
        String name,

        @NotBlank(message = "{description.notBlank}")
        @Size(max = 255, message = "{description.size.max}")
        String description,

        @NotNull(message = "{color.notNull}")
        Color wrapperColor,

        @NotNull(message = "{positions.notNull}")
        @NotEmpty(message = "{positions.notEmpty}")
        @Size(max = 5, message = "{positions.size.max}")
        Set<BouquetPosition> positions) {
}