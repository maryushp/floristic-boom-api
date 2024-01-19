package com.floristicboom.address.repository;

import com.floristicboom.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPostalCodeAndApartment(String postalCode, String apartment);
}