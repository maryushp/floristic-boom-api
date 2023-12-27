package com.floristicboom.address.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDTO (
    Long id,
    @Size(max = 40, message = "{city.size.max}")
    @NotBlank(message = "{city.notBlank}")
    String city,
    @Size(max = 40, message = "{street.size.max}")
    @NotBlank(message = "{street.notBlank}")
    String street,
    @Size(max = 5, message = "{house.size.max}")
    @NotBlank(message = "{house.notBlank}")
    String house,
    @Size(max = 5, message = "{apartment.size.max}")
    @NotBlank(message = "{apartment.notBlank}")
    String apartment,
    @Size(max = 6, message = "{postalCode.size.max}")
    @NotBlank(message = "{postalCode.notBlank}")
    String postalCode) {
}