package com.floristicboom.address.service;

import com.floristicboom.address.model.Address;
import com.floristicboom.address.model.AddressDTO;
import com.floristicboom.address.repository.AddressRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.ItemAlreadyExistsException;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public AddressDTO create(AddressDTO addressDTO) {
        Address address = entityToDtoMapper.toAddress(addressDTO);
        if (isAddressExist(address))
            throw new ItemAlreadyExistsException(String.format(ADDRESS_ALREADY_EXISTS, address.getPostalCode(), address.getApartment()));
        return entityToDtoMapper.toAddressDTO(addressRepository.save(address));
    }

    @Override
    public Page<AddressDTO> readAll(Pageable pageable) {
        return addressRepository.findAll(pageable).map(entityToDtoMapper::toAddressDTO);
    }

    @Override
    public AddressDTO findById(Long id) {
        return addressRepository.findById(id).map(entityToDtoMapper::toAddressDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(ADDRESS_NOT_FOUND_ID, id)));
    }

    @Override
    public AddressDTO findByPostalCodeAndApartment(String postalCode, String apartment) {
        return addressRepository.findByPostalCodeAndApartment(postalCode, apartment).map(entityToDtoMapper::toAddressDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(ADDRESS_NOT_FOUND_POSTAL_CODE_APARTMENT, postalCode, apartment)));
    }

    @Override
    public void delete(Long id) {
        addressRepository.findById(id).ifPresentOrElse(addressRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(ADDRESS_NOT_FOUND_ID, id));
                });
    }

    private boolean isAddressExist(Address address) {
        ExampleMatcher addressMatcher = ExampleMatcher.matching()
                .withIgnorePaths(ID, CITY, STREET, HOUSE)
                .withMatcher(POSTAL_CODE, exact())
                .withMatcher(APARTMENT, exact());

        return addressRepository.exists(Example.of(address, addressMatcher));
    }
}