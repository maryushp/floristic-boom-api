package com.floristicboom.utils.mappers;

import com.floristicboom.auth.models.RegisterRequest;
import com.floristicboom.bonus.model.Bonus;
import com.floristicboom.bonus.model.BonusDTO;
import com.floristicboom.bouquet.model.Bouquet;
import com.floristicboom.bouquet.model.BouquetCreationRequest;
import com.floristicboom.bouquet.model.BouquetDTO;
import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.user.model.User;
import com.floristicboom.user.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    FlowerDTO toFlowerDTO(Flower flower);

    Flower toFlower(FlowerDTO flowerDTO);

    DeliveryType toDeliveryType(DeliveryTypeDTO deliveryTypeDTO);

    DeliveryTypeDTO toDeliveryTypeDTO(DeliveryType deliveryType);

    BonusDTO toBonusDTO(Bonus bonus);

    Bonus toBonus(BonusDTO bonusDTO);

    UserDTO toUserDTO(User user);

    User toUser(RegisterRequest registerRequest);

    BouquetDTO toBouquetDTO(Bouquet bouquet);

    @Mapping(target = "flowers", ignore = true)
    Bouquet toBouquet(BouquetCreationRequest bouquetCreationRequest);
}