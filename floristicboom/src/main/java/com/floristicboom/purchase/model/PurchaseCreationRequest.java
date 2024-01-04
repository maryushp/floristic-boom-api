package com.floristicboom.purchase.model;

import com.floristicboom.purchasebouquet.model.PurchaseBouquetDTO;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record PurchaseCreationRequest (
        @NotNull(message = "{paymentType.notNull}")
        PaymentType paymentType,
        @NotNull(message = "{delivery.notNull}")
        Long deliveryId,
        Long bonusId,
        Set<PurchaseBouquetDTO> bouquets) {
}