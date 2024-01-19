package com.floristicboom.bonus.service;

import com.floristicboom.bonus.model.BonusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BonusService {
    BonusDTO create(BonusDTO bonusDTO);

    Page<BonusDTO> readAll(Pageable pageable);

    BonusDTO findById(Long id);

    BonusDTO findByPromoCode(String promoCode);

    void delete(Long id);
}