package com.floristicboom.bouquet.model;

import com.floristicboom.bouquetflower.model.BouquetFlowerDTO;
import com.floristicboom.user.model.UserDTO;
import com.floristicboom.utils.Color;

import java.math.BigDecimal;
import java.util.Set;

public record BouquetDTO(
        Long id,
        String name,
        BigDecimal price,
        String description,
        String imageUri,
        Color wrapperColor,
        Boolean isCustom,
        Set<BouquetFlowerDTO> flowers,
        UserDTO user) {
}