package com.floristicboom.bouquetflower.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BouquetFlowerPK implements Serializable {
    @Column(name = "bouquet_id")
    private Long bouquetId;
    @Column(name = "flower_id")
    private Long flowerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BouquetFlowerPK that = (BouquetFlowerPK) o;

        return new EqualsBuilder().append(bouquetId, that.bouquetId).append(flowerId, that.flowerId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(bouquetId).append(flowerId).toHashCode();
    }
}