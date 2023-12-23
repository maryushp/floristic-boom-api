package com.floristicboom.utils.mappers;

import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.model.FlowerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    FlowerDTO toFlowerDTO(Flower flower);

    Flower toFlower(FlowerDTO flowerDTO);
}