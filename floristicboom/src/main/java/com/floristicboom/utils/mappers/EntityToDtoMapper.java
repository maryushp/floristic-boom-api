package com.floristicboom.utils.mappers;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.user.model.User;
import com.floristicboom.user.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    FlowerDTO toFlowerDTO(Flower flower);

    Flower toFlower(FlowerDTO flowerDTO);

    UserDTO toUserDTO(User user);

    User toUser(RegisterRequest registerRequest);
}