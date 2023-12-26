package com.floristicboom.delivery.type.service;

import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryTypeService {
    DeliveryTypeDTO create(DeliveryTypeDTO deliveryTypeDTO);

    Page<DeliveryTypeDTO> readAll(Pageable pageable);

    DeliveryTypeDTO findById(Long id);

    void delete(Long id);
}