package com.floristicboom.bouquetflower.model;

import com.floristicboom.flower.model.FlowerDTO;


public record BouquetFlowerDTO(FlowerDTO flower, Integer quantity) {
}