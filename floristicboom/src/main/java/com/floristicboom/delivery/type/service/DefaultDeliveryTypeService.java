package com.floristicboom.delivery.type.service;

import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
import com.floristicboom.delivery.type.repository.DeliveryTypeRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.ItemAlreadyExistsException;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryTypeService implements DeliveryTypeService{
    private final DeliveryTypeRepository deliveryTypeRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public DeliveryTypeDTO create(DeliveryTypeDTO deliveryTypeDTO) {
        DeliveryType deliveryType = entityToDtoMapper.toDeliveryType(deliveryTypeDTO);
        if (isDeliveryTypeExist(deliveryType))
            throw new ItemAlreadyExistsException(String.format(DELIVERY_TYPE_ALREADY_EXISTS, deliveryType.getName()));
        return entityToDtoMapper.toDeliveryTypeDTO(deliveryTypeRepository.save(deliveryType));
    }

    @Override
    public Page<DeliveryTypeDTO> readAll(Pageable pageable) {
        return deliveryTypeRepository.findAll(pageable).map(entityToDtoMapper::toDeliveryTypeDTO);
    }

    @Override
    public DeliveryTypeDTO findById(Long id) {
        return deliveryTypeRepository.findById(id).map(entityToDtoMapper::toDeliveryTypeDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(DELIVERY_TYPE_NOT_FOUND, id)));
    }

    @Override
    public void delete(Long id) {
        deliveryTypeRepository.findById(id).ifPresentOrElse(deliveryTypeRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(DELIVERY_TYPE_NOT_FOUND, id));
                });
    }

    private boolean isDeliveryTypeExist(DeliveryType deliveryType) {
        ExampleMatcher deliveryTypeMatcher = ExampleMatcher.matching()
                .withIgnorePaths(PRICE)
                .withMatcher(NAME, exact());

        return deliveryTypeRepository.exists(Example.of(deliveryType, deliveryTypeMatcher));
    }
}