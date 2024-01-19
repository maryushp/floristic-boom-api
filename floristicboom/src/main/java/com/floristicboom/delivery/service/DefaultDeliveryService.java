package com.floristicboom.delivery.service;

import com.floristicboom.address.model.Address;
import com.floristicboom.address.service.AddressService;
import com.floristicboom.delivery.model.Delivery;
import com.floristicboom.delivery.model.DeliveryDTO;
import com.floristicboom.delivery.repository.DeliveryRepository;
import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.delivery.type.service.DeliveryTypeService;
import com.floristicboom.user.model.User;
import com.floristicboom.user.service.UserService;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.DELIVERY_NOT_FOUND_ID;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final DeliveryTypeService deliveryTypeService;

    @Override
    public DeliveryDTO create(DeliveryDTO deliveryDTO) {
        Delivery delivery = entityToDtoMapper.toDelivery(deliveryDTO);

        DeliveryType deliveryType =
                entityToDtoMapper.toDeliveryType(deliveryTypeService.findById(delivery.getDeliveryType().getId()));
        Address address = entityToDtoMapper.toAddress(addressService.findById(delivery.getAddress().getId()));

        if (delivery.getCourier() != null) {
            User courier = entityToDtoMapper.toUser(userService.findById(delivery.getCourier().getId()));
            delivery.setCourier(courier);
        }

        delivery.setDeliveryType(deliveryType);
        delivery.setAddress(address);

        return entityToDtoMapper.toDeliveryDTO(deliveryRepository.save(delivery));
    }

    @Override
    public Page<DeliveryDTO> readAll(Pageable pageable) {
        return deliveryRepository.findAll(pageable).map(entityToDtoMapper::toDeliveryDTO);
    }

    @Override
    public DeliveryDTO findById(Long id) {
        return deliveryRepository.findById(id).map(entityToDtoMapper::toDeliveryDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(DELIVERY_NOT_FOUND_ID, id)));
    }

    @Override
    public void delete(Long id) {
        deliveryRepository.findById(id).ifPresentOrElse(deliveryRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(DELIVERY_NOT_FOUND_ID, id));
                });
    }
}