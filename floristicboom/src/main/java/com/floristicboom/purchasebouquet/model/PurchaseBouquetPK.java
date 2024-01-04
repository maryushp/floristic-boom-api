package com.floristicboom.purchasebouquet.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBouquetPK implements Serializable {
    private Long purchaseId;
    private Long bouquetId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        PurchaseBouquetPK purchaseBouquetPK = (PurchaseBouquetPK) obj;
        return purchaseId != null && Objects.equals(purchaseId, purchaseBouquetPK.purchaseId)
                && bouquetId != null && Objects.equals(bouquetId, purchaseBouquetPK.bouquetId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}