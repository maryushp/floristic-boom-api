package com.floristicboom.delivery.type.controller;

import com.floristicboom.delivery.type.model.DeliveryTypeDTO;
import com.floristicboom.delivery.type.service.DeliveryTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/delivery-type")
@RequiredArgsConstructor
public class DeliveryTypeController {
    private final DeliveryTypeService deliveryTypeService;

    @PostMapping
    public ResponseEntity<DeliveryTypeDTO> create (@RequestBody @Valid DeliveryTypeDTO deliveryTypeDTO) {
        DeliveryTypeDTO createdDeliveryType = deliveryTypeService.create(deliveryTypeDTO);
        return ResponseEntity.created(URI.create("/api/v1/delivery/type/" + createdDeliveryType.id())).body(createdDeliveryType);
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryTypeDTO>> readAll (@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(deliveryTypeService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryTypeDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(deliveryTypeService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        deliveryTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}