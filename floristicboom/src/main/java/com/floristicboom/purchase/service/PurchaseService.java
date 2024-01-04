package com.floristicboom.purchase.service;

import com.floristicboom.purchase.model.PurchaseCreationRequest;
import com.floristicboom.purchase.model.PurchaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseService {
    PurchaseDTO create(PurchaseCreationRequest purchaseCreationRequest);

    Page<PurchaseDTO> readAll(Pageable pageable);

    Page<PurchaseDTO> findAllByUserId(Pageable pageable, Long userId);

    PurchaseDTO findById(Long id);

    void delete(Long id);
}