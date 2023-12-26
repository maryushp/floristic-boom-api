package com.floristicboom.delivery.service;

import com.floristicboom.address.model.Address;
import com.floristicboom.address.repository.AddressRepository;
import com.floristicboom.delivery.model.Delivery;
import com.floristicboom.delivery.model.DeliveryDTO;
import com.floristicboom.delivery.repository.DeliveryRepository;
import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.delivery.type.repository.DeliveryTypeRepository;
import com.floristicboom.user.model.User;
import com.floristicboom.user.repository.UserRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final DeliveryTypeRepository deliveryTypeRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public DeliveryDTO create(DeliveryDTO deliveryDTO) {
        Delivery delivery = entityToDtoMapper.toDelivery(deliveryDTO);

        DeliveryType deliveryType = getExistingDeliveryType(delivery.getDeliveryType().getId());
        User courier = getExistingUser(delivery.getCourier().getId());
        Address address = getExistingAddress(delivery.getAddress().getId());

        delivery.setDeliveryType(deliveryType);
        delivery.setCourier(courier);
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

    private User getExistingUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchItemException(String.format(USER_WITH_ID_NOT_FOUND, userId)));
    }

    private DeliveryType getExistingDeliveryType(Long deliveryTypeId) {
        return deliveryTypeRepository.findById(deliveryTypeId)
                .orElseThrow(() -> new NoSuchItemException(String.format(DELIVERY_TYPE_NOT_FOUND, deliveryTypeId)));
    }

    private Address getExistingAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NoSuchItemException(String.format(ADDRESS_NOT_FOUND_ID, addressId)));
    }
}