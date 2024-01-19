package com.floristicboom.user.model;

import com.floristicboom.address.model.AddressDTO;

import java.util.List;

public record UserDTO(
        Long id ,
        String email,
        String firstName,
        String lastName,
        String phone,
        String imageUri,
        List<AddressDTO> addresses ) {
}