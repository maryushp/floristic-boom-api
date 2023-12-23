package com.floristicboom.flower.service;

import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.repository.FlowerRepository;
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
public class DefaultFlowerService implements FlowerService {
    private final FlowerRepository flowerRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public FlowerDTO create(FlowerDTO flowerDTO) {
        Flower flower = entityToDtoMapper.toFlower(flowerDTO);
        if (isFlowerExist(flower))
            throw new ItemAlreadyExistsException(FLOWER_ALREADY_EXISTS);
        return entityToDtoMapper.toFlowerDTO(flowerRepository.save(flower));
    }

    @Override
    public Page<FlowerDTO> getAll(Pageable pageable) {
        return flowerRepository.findAll(pageable).map(entityToDtoMapper::toFlowerDTO);
    }

    @Override
    public FlowerDTO findById(Long id) {
        return flowerRepository.findById(id).map(entityToDtoMapper::toFlowerDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(FLOWER_NOT_FOUND, id)));
    }

    @Override
    public void delete(Long id) {
        flowerRepository.findById(id).ifPresentOrElse(flowerRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(FLOWER_NOT_FOUND, id));
                });
    }

    private boolean isFlowerExist(Flower flower) {
        ExampleMatcher flowerMatcher = ExampleMatcher.matching()
                .withIgnorePaths(IMAGE_URI, AVAILABLE_QUANTITY)
                .withMatcher(NAME, exact())
                .withMatcher(DESCRIPTION, exact())
                .withMatcher(PRICE,
                        exact())
                .withMatcher(COLOR, exact());
        return flowerRepository.exists(Example.of(flower, flowerMatcher));
    }

}