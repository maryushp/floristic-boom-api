package com.floristicboom.purchase.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.floristicboom.bonus.model.Bonus;
import com.floristicboom.delivery.model.Delivery;
import com.floristicboom.purchasebouquet.model.PurchaseBouquet;
import com.floristicboom.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private User employee;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;
    @ManyToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private Delivery delivery;
    @ManyToOne
    @JoinColumn(name = "bonus_id", referencedColumnName = "id")
    private Bonus bonus;
    @OneToMany(mappedBy = "purchase")
    private Set<PurchaseBouquet> bouquets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        return new EqualsBuilder()
                .append(id, purchase.id)
                .append(creationDate, purchase.creationDate)
                .append(lastUpdateDate, purchase.lastUpdateDate)
                .append(paymentDate, purchase.paymentDate)
                .append(price, purchase.price)
                .append(status, purchase.status)
                .append(paymentType, purchase.paymentType)
                .append(employee, purchase.employee)
                .append(client, purchase.client)
                .append(delivery, purchase.delivery)
                .append(bonus, purchase.bonus)
                .append(bouquets, purchase.bouquets)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(creationDate)
                .append(lastUpdateDate)
                .append(paymentDate)
                .append(price)
                .append(status)
                .append(paymentType)
                .append(employee)
                .append(client)
                .append(delivery)
                .append(bonus)
                .append(bouquets)
                .toHashCode();
    }
}