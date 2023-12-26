package com.floristicboom.address.controller;

import com.floristicboom.address.model.AddressDTO;
import com.floristicboom.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDTO> create (@RequestBody @Valid AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.create(addressDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> readAll (@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(addressService.readAll(pageable));
    }

    @GetMapping("/find-address")
    public ResponseEntity<AddressDTO> findByPostalCodeAndApartment (@RequestParam("postal_code") String postalCode, @RequestParam("apartment") String apartment) {
        return ResponseEntity.ok(addressService.findByPostalCodeAndApartment(postalCode, apartment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}