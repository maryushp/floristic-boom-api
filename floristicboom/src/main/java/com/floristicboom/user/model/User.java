package com.floristicboom.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.floristicboom.address.model.Address;
import com.floristicboom.credentials.model.Credentials;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 45)
    private String firstName;
    @Column(nullable = false, length = 45)
    private String lastName;
    @Column(unique = true, nullable = false, length = 12)
    private String phone;
    @Builder.Default
    private String imageUri = "";
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Address> addresses;
    @OneToOne
    @JoinColumn(name = "credentials_id")
    @JsonIgnore
    private Credentials credentials;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder().append(id, user.id)
                .append(email, user.email)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(phone, user.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(email)
                .append(firstName)
                .append(lastName)
                .append(phone)
                .toHashCode();
    }
}
