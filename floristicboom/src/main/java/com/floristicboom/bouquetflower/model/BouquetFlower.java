package com.floristicboom.bouquetflower.model;

import com.floristicboom.bouquet.model.Bouquet;
import com.floristicboom.flower.model.Flower;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "bouquet_flower")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BouquetFlower {
    @EmbeddedId
    private BouquetFlowerPK bouquetFlowerPK;
    @ManyToOne
    @MapsId("bouquetId")
    private Bouquet bouquet;
    @ManyToOne
    @MapsId("flowerId")
    private Flower flower;
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BouquetFlower that = (BouquetFlower) o;

        return new EqualsBuilder()
                .append(bouquetFlowerPK, that.bouquetFlowerPK)
                .append(quantity, that.quantity)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(bouquetFlowerPK)
                .append(quantity)
                .toHashCode();
    }
}