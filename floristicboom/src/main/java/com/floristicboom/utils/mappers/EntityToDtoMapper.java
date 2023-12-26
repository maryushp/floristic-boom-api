package com.floristicboom.utils.mappers;

import com.floristicboom.address.model.Address;
import com.floristicboom.address.model.AddressDTO;
import com.floristicboom.bonus.model.Bonus;
import com.floristicboom.bonus.model.BonusDTO;
import com.floristicboom.delivery.model.Delivery;
import com.floristicboom.delivery.model.DeliveryDTO;
import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
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

    DeliveryType toDeliveryType(DeliveryTypeDTO deliveryTypeDTO);

    DeliveryTypeDTO toDeliveryTypeDTO(DeliveryType deliveryType);

    BonusDTO toBonusDTO(Bonus bonus);

    Bonus toBonus(BonusDTO bonusDTO);

    UserDTO toUserDTO(User user);

    User toUser(RegisterRequest registerRequest);

    Address toAddress(AddressDTO addressDTO);

    AddressDTO toAddressDTO(Address address);

    Delivery toDelivery(DeliveryDTO deliveryDTO);

    DeliveryDTO toDeliveryDTO(Delivery delivery);
}