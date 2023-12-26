package com.floristicboom.delivery.controller;

import com.floristicboom.delivery.model.DeliveryDTO;
import com.floristicboom.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryDTO> create (@RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.create(deliveryDTO));
    }

    @GetMapping
    public ResponseEntity<Page<DeliveryDTO>> readAll (@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(deliveryService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        deliveryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}