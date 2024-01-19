package com.floristicboom.delivery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.floristicboom.address.model.Address;
import com.floristicboom.delivery.type.model.DeliveryType;
import com.floristicboom.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryDate;
    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private User courier;
    @ManyToOne
    @JoinColumn(name = "delivery_type_id", referencedColumnName = "id", nullable = false)
    private DeliveryType deliveryType;
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        return new EqualsBuilder()
                .append(id, delivery.id)
                .append(deliveryDate, delivery.deliveryDate)
                .append(courier, delivery.courier)
                .append(deliveryType, delivery.deliveryType)
                .append(address, delivery.address)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(deliveryDate)
                .append(courier)
                .append(deliveryType)
                .append(address)
                .toHashCode();
    }
}