package com.floristicboom.purchasebouquet.model;

import com.floristicboom.bouquet.model.Bouquet;
import com.floristicboom.purchase.model.Purchase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "purchase_bouquet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBouquet {
    @EmbeddedId
    private PurchaseBouquetPK purchaseBouquetPK;
    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    @ManyToOne
    @MapsId("bouquetId")
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet;
    private int quantity;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        PurchaseBouquet purchaseBouquet = (PurchaseBouquet) obj;
        return purchaseBouquetPK != null && Objects.equals(purchaseBouquetPK, purchaseBouquet.getPurchaseBouquetPK());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}