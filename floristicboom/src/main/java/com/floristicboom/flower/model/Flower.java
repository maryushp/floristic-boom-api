package com.floristicboom.flower.model;

import com.floristicboom.utils.Color;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "flower")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Color color;
    private Integer availableQuantity;
    private String imageUri;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Flower flower = (Flower) o;

        return new EqualsBuilder().append(id, flower.id).append(name, flower.name).append(description,
                flower.description).append(price, flower.price).append(color, flower.color).append(availableQuantity,
                flower.availableQuantity).append(imageUri, flower.imageUri).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(price).append(color).toHashCode();
    }
}