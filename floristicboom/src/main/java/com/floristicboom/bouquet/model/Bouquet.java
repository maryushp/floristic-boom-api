package com.floristicboom.bouquet.model;

import com.floristicboom.bouquetflower.model.BouquetFlower;
import com.floristicboom.user.model.User;
import com.floristicboom.utils.Color;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bouquet")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bouquet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String name;
    private BigDecimal price;
    @Column(nullable = false)
    private String description;
    @Builder.Default
    private String imageUri = "";
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color wrapperColor;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isCustom = false;
    @OneToMany(mappedBy = "bouquet")
    private Set<BouquetFlower> flowers;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Bouquet bouquet = (Bouquet) o;

        return new EqualsBuilder()
                .append(id, bouquet.id)
                .append(name, bouquet.name)
                .append(price, bouquet.price)
                .append(description, bouquet.description)
                .append(isCustom, bouquet.isCustom)
                .append(flowers, bouquet.flowers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id).append(name)
                .append(price)
                .append(description)
                .append(isCustom)
                .append(flowers)
                .toHashCode();
    }
}