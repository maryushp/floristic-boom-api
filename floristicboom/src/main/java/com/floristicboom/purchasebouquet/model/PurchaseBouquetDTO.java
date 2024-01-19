package com.floristicboom.purchasebouquet.model;

import com.floristicboom.bouquet.model.BouquetDTO;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseBouquetDTO {
    @NotNull(message = "{bouquet.notnull}")
    private BouquetDTO bouquet;
    @NotNull(message = "{quantity.notnull}")
    @Positive(message = "{quantity.positive}")
    private int quantity;
}