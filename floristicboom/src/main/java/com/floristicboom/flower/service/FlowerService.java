package com.floristicboom.flower.service;

import com.floristicboom.flower.model.FlowerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FlowerService {
    FlowerDTO create(FlowerDTO flowerDTO);

    Page<FlowerDTO> readAll(Pageable pageable);

    FlowerDTO findById(Long id);

    void delete(Long id);
}