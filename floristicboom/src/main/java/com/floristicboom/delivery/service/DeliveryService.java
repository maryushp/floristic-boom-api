package com.floristicboom.delivery.service;

import com.floristicboom.delivery.model.DeliveryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {
    DeliveryDTO create(DeliveryDTO deliveryDTO);

    Page<DeliveryDTO> readAll(Pageable pageable);

    DeliveryDTO findById(Long id);

    void delete(Long id);
}