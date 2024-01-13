package com.floristicboom.flower.service;

import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.repository.FlowerRepository;
import com.floristicboom.utils.Color;
import com.floristicboom.utils.exceptionhandler.exceptions.FlowerUnavaliableException;
import com.floristicboom.utils.exceptionhandler.exceptions.ItemAlreadyExistsException;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public Page<FlowerDTO> readAll(Pageable pageable) {
        return flowerRepository.findAll(pageable).map(entityToDtoMapper::toFlowerDTO);
    }

    @Override
    public FlowerDTO findById(Long id) {
        return flowerRepository.findById(id).map(entityToDtoMapper::toFlowerDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(FLOWER_NOT_FOUND, id)));
    }

    @Override
    public Page<FlowerDTO> searchFlowers(Pageable pageable,
                                         Integer minPrice, Integer maxPrice,
                                         String partialName,
                                         String color) {
        Specification<Flower> spec = Specification.where(null);

        if (minPrice != null && maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"), BigDecimal.valueOf(minPrice),
                            BigDecimal.valueOf(maxPrice)));
        }

        if (partialName != null && !partialName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + partialName.toLowerCase() + "%"));
        }

        if (color != null && !color.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("color"), Color.valueOf(color)));
        }

        return flowerRepository.findAll(spec, pageable)
                .map(entityToDtoMapper::toFlowerDTO);
    }

    @Override
    public void delete(Long id) {
        flowerRepository.findById(id).ifPresentOrElse(flowerRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(FLOWER_NOT_FOUND, id));
                });
    }

    @Override
    public void changeFlowerQuantity(Flower flower, Integer quantity) {
        if (flower.getAvailableQuantity() < quantity)
            throw new FlowerUnavaliableException(String.format(NOT_ENOUGH_FLOWERS, flower.getName(),
                    flower.getColor()));
        flower.setAvailableQuantity(flower.getAvailableQuantity() - quantity);
        flowerRepository.save(flower);
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