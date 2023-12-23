package com.floristicboom.bonus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonus")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="promocode")
    private String promoCode;
    private BigDecimal discount;
    @JsonFormat(pattern="dd-MM-yyyyTHH:mm:ss")
    private LocalDateTime durationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Bonus bonus = (Bonus) o;

        return new EqualsBuilder().append(id, bonus.getId()).append(promoCode, bonus.promoCode).append(discount,
                bonus.discount).append(durationDate, bonus.durationDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(promoCode).append(discount).append(durationDate).toHashCode();
    }
}