package com.floristicboom.address.service;

import com.floristicboom.address.model.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    AddressDTO create(AddressDTO addressDTO);

    Page<AddressDTO> readAll(Pageable pageable);

    AddressDTO findById(Long id);

    AddressDTO findByPostalCodeAndApartment(String postalCode, String apartment);

    void delete(Long id);
}