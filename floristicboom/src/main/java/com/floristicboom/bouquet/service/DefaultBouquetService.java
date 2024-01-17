package com.floristicboom.bouquet.service;

import com.floristicboom.bouquet.model.Bouquet;
import com.floristicboom.bouquet.model.BouquetCreationRequest;
import com.floristicboom.bouquet.model.BouquetDTO;
import com.floristicboom.bouquet.model.BouquetPosition;
import com.floristicboom.bouquet.repository.BouquetRepository;
import com.floristicboom.bouquetflower.model.BouquetFlower;
import com.floristicboom.bouquetflower.model.BouquetFlowerPK;
import com.floristicboom.bouquetflower.service.BouquetFlowerService;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.credentials.model.Role;
import com.floristicboom.flower.model.Flower;
import com.floristicboom.flower.service.FlowerService;
import com.floristicboom.utils.exceptionhandler.exceptions.ItemAlreadyExistsException;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.floristicboom.utils.Constants.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class DefaultBouquetService implements BouquetService {
    private final BouquetRepository bouquetRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final FlowerService flowerService;
    private final BouquetFlowerService bouquetFlowerService;

    @Override
    @Transactional
    public BouquetDTO create(BouquetCreationRequest bouquetCreationRequest) {
        Bouquet bouquet = entityToDtoMapper.toBouquet(bouquetCreationRequest);
        if (isBouquetExist(bouquet))
            throw new ItemAlreadyExistsException(BOUQUET_ALREADY_EXISTS);
        bouquet = bouquetRepository.save(bouquet);
        Map<Flower, Integer> flowerQuantityMap =
                createFlowerQuantityMap(bouquetCreationRequest.positions());
        Set<BouquetFlower> bouquetFlowers = createBouquetFlowerSet(flowerQuantityMap, bouquet);
        bouquet.setFlowers(bouquetFlowers);
        bouquet.setPrice(getBouquetPrice(flowerQuantityMap));
        setBouquetPropertiesBasedOnUserRole(bouquet);
        return entityToDtoMapper.toBouquetDTO(bouquet);
    }

    @Override
    public Page<BouquetDTO> readAll(Pageable pageable) {
        return bouquetRepository.readBouquetByIsCustomFalse(pageable).map(entityToDtoMapper::toBouquetDTO);
    }

    @Override
    public Page<BouquetDTO> findAllByUserId(Pageable pageable, Long userId) {
        return bouquetRepository.findAllByUserId(pageable, userId).map(entityToDtoMapper::toBouquetDTO);
    }

    @Override
    public BouquetDTO findById(Long id) {
        return bouquetRepository.findById(id).map(entityToDtoMapper::toBouquetDTO).orElseThrow(
                () -> new NoSuchItemException(String.format(BOUQUET_NOT_FOUND_ID, id))
        );
    }

    @Override
    public Page<BouquetDTO> searchBouquets(Pageable pageable,
                                           Integer minSize, Integer maxSize,
                                           Integer minPrice, Integer maxPrice,
                                           String partialName) {
        Specification<Bouquet> spec = Specification.where(null);

        if (minSize != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Expression<Long> sumExpression = getFlowerQuantity(query.subquery(Long.class), criteriaBuilder, root);
                return criteriaBuilder.greaterThanOrEqualTo(sumExpression, minSize.longValue());
            });
        }

        if (maxSize != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Expression<Long> sumExpression = getFlowerQuantity(query.subquery(Long.class), criteriaBuilder, root);
                return criteriaBuilder.lessThanOrEqualTo(sumExpression, maxSize.longValue());
            });
        }

        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE), BigDecimal.valueOf(minPrice)));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(PRICE), BigDecimal.valueOf(maxPrice)));
        }

        if (partialName != null && !partialName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(NAME), "%" + partialName + "%"));
        }

        return bouquetRepository.findAll(spec, pageable)
                .map(entityToDtoMapper::toBouquetDTO);
    }

    private Expression<Long> getFlowerQuantity(Subquery<Long> query, CriteriaBuilder criteriaBuilder,
                                               Root<Bouquet> root) {
        Root<BouquetFlower> subRoot = query.from(BouquetFlower.class);

        query.select(criteriaBuilder.sum(subRoot.get(QUANTITY)))
                .where(criteriaBuilder.equal(subRoot.get(BOUQUET), root));

        return query.getSelection();
    }

    @Override
    public void delete(Long id) {
        bouquetRepository.findById(id).ifPresentOrElse(bouquetRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(BOUQUET_NOT_FOUND_ID, id));
                });
    }

    private boolean isBouquetExist(Bouquet bouquet) {
        ExampleMatcher bouquetMatcher = ExampleMatcher.matching()
                .withIgnorePaths(IMAGE_URI, NAME)
                .withMatcher(DESCRIPTION, exact())
                .withMatcher(PRICE, exact())
                .withMatcher(COLOR, exact())
                .withMatcher(USER_ID, exact());
        return bouquetRepository.exists(Example.of(bouquet, bouquetMatcher));
    }

    private BigDecimal getBouquetPrice(Map<Flower, Integer> flowerQuantityMap) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<Flower, Integer> entry : flowerQuantityMap.entrySet()) {
            Flower flower = entry.getKey();
            Integer quantity = entry.getValue();
            BigDecimal flowerPrice = flower.getPrice();
            BigDecimal lineItemCost = flowerPrice.multiply(BigDecimal.valueOf(quantity));
            totalCost = totalCost.add(lineItemCost);
        }
        return totalCost;
    }

    private Map<Flower, Integer> createFlowerQuantityMap(Set<BouquetPosition> bouquetPositions) {
        Map<Flower, Integer> flowerQuantityMap = new HashMap<>();
        for (BouquetPosition position : bouquetPositions) {
            Flower flower =
                    entityToDtoMapper.toFlower(flowerService.findById(position.flowerId()));
            flowerService.changeFlowerQuantity(flower, position.quantity());
            flowerQuantityMap.put(flower, position.quantity());
        }
        return flowerQuantityMap;
    }


    private Set<BouquetFlower> createBouquetFlowerSet(Map<Flower, Integer> flowerQuantityMap,
                                                      Bouquet savedPurchase) {
        return flowerQuantityMap.entrySet().stream().map(entry -> {
            Flower flower = entry.getKey();
            Integer quantity = entry.getValue();
            BouquetFlowerPK bouquetFlowerPK = new BouquetFlowerPK(savedPurchase.getId(),
                    flower.getId());
            return bouquetFlowerService.create(
                    new BouquetFlower(bouquetFlowerPK, savedPurchase, flower, quantity));
        }).collect(Collectors.toSet());
    }

    private void setBouquetPropertiesBasedOnUserRole(Bouquet bouquet) {
        Credentials credentials = (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bouquet.setIsCustom(!credentials.getRole().equals(Role.ADMIN));
        bouquet.setUser(credentials.getUser());
    }
}