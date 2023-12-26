package com.floristicboom.delivery.model;

import com.floristicboom.address.model.AddressDTO;
import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
import com.floristicboom.user.model.UserDTO;

public record DeliveryDTO(
        Long id,
        String deliveryDate,
        UserDTO courier,
        DeliveryTypeDTO deliveryType,
        AddressDTO address) {
}