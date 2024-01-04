package com.floristicboom.purchase.model;

import com.floristicboom.bonus.model.BonusDTO;
import com.floristicboom.delivery.model.DeliveryDTO;
import com.floristicboom.purchasebouquet.model.PurchaseBouquetDTO;
import com.floristicboom.user.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private LocalDateTime paymentDate;
    private BigDecimal price;
    private Status status;
    private PaymentType paymentType;
    private UserDTO employee;
    private UserDTO client;
    private DeliveryDTO delivery;
    private BonusDTO bonus;
    private Set<PurchaseBouquetDTO> bouquets;
}