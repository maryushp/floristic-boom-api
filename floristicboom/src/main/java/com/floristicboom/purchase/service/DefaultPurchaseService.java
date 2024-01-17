package com.floristicboom.purchase.service;

import com.floristicboom.address.service.AddressService;
import com.floristicboom.bonus.model.Bonus;
import com.floristicboom.bonus.service.BonusService;
import com.floristicboom.bouquet.model.Bouquet;
import com.floristicboom.bouquet.service.BouquetService;
import com.floristicboom.credentials.model.Credentials;
import com.floristicboom.delivery.model.Delivery;
import com.floristicboom.delivery.repository.DeliveryRepository;
import com.floristicboom.delivery.type.service.DeliveryTypeService;
import com.floristicboom.purchase.model.Purchase;
import com.floristicboom.purchase.model.PurchaseCreationRequest;
import com.floristicboom.purchase.model.PurchaseDTO;
import com.floristicboom.purchase.model.Status;
import com.floristicboom.purchase.repository.PurchaseRepository;
import com.floristicboom.purchasebouquet.model.PurchaseBouquet;
import com.floristicboom.purchasebouquet.model.PurchaseBouquetDTO;
import com.floristicboom.purchasebouquet.model.PurchaseBouquetPK;
import com.floristicboom.purchasebouquet.repository.PurchaseBouquetRepository;
import com.floristicboom.user.model.User;
import com.floristicboom.user.repository.UserRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.floristicboom.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class DefaultPurchaseService implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final PurchaseBouquetRepository purchaseBouquetRepository;
    private final UserRepository userRepository;
    private final BonusService bonusService;
    private final BouquetService bouquetService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryTypeService deliveryTypeService;
    private final AddressService addressService;

    @Override
    @Transactional
    public PurchaseDTO create(PurchaseCreationRequest purchaseCreationRequest) {
        Credentials currentCredentials =
                (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User client =
                userRepository.findByEmail(currentCredentials.getUsername()).orElseThrow(() -> new NoSuchItemException(String.format(USER_WITH_EMAIL_NOT_FOUND, currentCredentials.getUsername())));

        Delivery delivery = Delivery.builder()
                .address(entityToDtoMapper.toAddress(addressService.findById(purchaseCreationRequest.addressId())))
                .deliveryType(entityToDtoMapper.toDeliveryType(deliveryTypeService.findById(purchaseCreationRequest.deliveryTypeId())))
                .build();

        delivery = deliveryRepository.save(delivery);

        Purchase purchase = Purchase.builder()
                .creationDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now())
                .status(Status.PLACED)
                .paymentType(purchaseCreationRequest.paymentType())
                .client(client)
                .delivery(delivery)
                .build();

        if (purchaseCreationRequest.bonusId() != null) {
            Bonus bonus = entityToDtoMapper.toBonus(bonusService.findById(purchaseCreationRequest.bonusId()));
            purchase.setBonus(bonus);
        }

        purchase = purchaseRepository.save(purchase);

        Map<Bouquet, Integer> bouquetQuantityMap = createBouquetQuantityMap(purchaseCreationRequest.bouquets());
        Set<PurchaseBouquet> purchaseBouquets = createPurchaseBouquetSet(bouquetQuantityMap, purchase);

        purchase.setPrice(getPurchasePrice(bouquetQuantityMap, purchase.getBonus(), purchase.getDelivery()));
        purchase.setBouquets(purchaseBouquets);

        return entityToDtoMapper.toPurchaseDTO(purchase);
    }

    @Override
    public Page<PurchaseDTO> readAll(Pageable pageable) {
        return purchaseRepository.findAll(pageable).map(entityToDtoMapper::toPurchaseDTO);
    }

    @Override
    public Page<PurchaseDTO> findAllByUserId(Pageable pageable, Long userId) {
        return purchaseRepository.findAllByClientId(pageable, userId).map(entityToDtoMapper::toPurchaseDTO);
    }

    @Override
    public PurchaseDTO findById(Long id) {
        return purchaseRepository.findById(id).map(entityToDtoMapper::toPurchaseDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(PURCHASE_NOT_FOUND_ID, id))
                );
    }

    @Override
    public void delete(Long id) {
        purchaseRepository.findById(id).ifPresentOrElse(purchaseRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(PURCHASE_NOT_FOUND_ID, id));
                });
    }

    private Set<PurchaseBouquet> createPurchaseBouquetSet(Map<Bouquet, Integer> bouquetQuantityMap, Purchase purchase) {
        return bouquetQuantityMap.entrySet().stream().map(entry -> {
            Bouquet bouquet = entry.getKey();
            Integer quantity = entry.getValue();
            PurchaseBouquetPK purchaseBouquetPK = new PurchaseBouquetPK(purchase.getId(), bouquet.getId());
            PurchaseBouquet purchaseBouquet = new PurchaseBouquet(purchaseBouquetPK, purchase, bouquet, quantity);
            return purchaseBouquetRepository.save(purchaseBouquet);
        }).collect(Collectors.toSet());
    }

    private BigDecimal getPurchasePrice(Map<Bouquet, Integer> bouquetQuantityMap, Bonus bonus, Delivery delivery) {
        BigDecimal price = BigDecimal.ZERO;
        for (Map.Entry<Bouquet, Integer> entry : bouquetQuantityMap.entrySet()) {
            price = price.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        price = price.add(delivery.getDeliveryType().getPrice());

        if (bonus != null) {
            price = price.subtract(price.multiply(bonus.getDiscount()));
        }

        return price;
    }

    private Map<Bouquet, Integer> createBouquetQuantityMap(Set<PurchaseBouquetDTO> positions) {
        Map<Bouquet, Integer> bouquetQuantityMap = new HashMap<>();
        for (PurchaseBouquetDTO position : positions) {
            Bouquet bouquet = entityToDtoMapper.toBouquet(bouquetService.findById(position.getBouquet().id()));
            bouquetQuantityMap.put(bouquet, position.getQuantity());
        }
        return bouquetQuantityMap;
    }
}