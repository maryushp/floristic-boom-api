package com.floristicboom.flower.service;

import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.repository.FlowerRepository;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFlowerService implements FlowerService {
    private final FlowerRepository flowerRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public Page<FlowerDTO> getAll(Pageable pageable) {
        return flowerRepository.findAll(pageable).map(entityToDtoMapper::toFlowerDTO);
    }
}