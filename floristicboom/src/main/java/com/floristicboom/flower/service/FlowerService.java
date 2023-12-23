package com.floristicboom.flower.service;

import com.floristicboom.flower.model.FlowerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FlowerService {
    Page<FlowerDTO> getAll(Pageable pageable);

    FlowerDTO findById(Long id);

    FlowerDTO create(FlowerDTO flowerDTO);

    void delete(Long id);
}