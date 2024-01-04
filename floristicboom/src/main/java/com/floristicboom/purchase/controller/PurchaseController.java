package com.floristicboom.purchase.controller;

import com.floristicboom.purchase.model.PurchaseCreationRequest;
import com.floristicboom.purchase.model.PurchaseDTO;
import com.floristicboom.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseDTO> create(@RequestBody @Valid PurchaseCreationRequest purchaseCreationRequest) {
        PurchaseDTO createdPurchase = purchaseService.create(purchaseCreationRequest);
        return ResponseEntity.created(URI.create("/api/v1/purchase/" + createdPurchase.getId())).body(createdPurchase);
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseDTO>> readAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(purchaseService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Page<PurchaseDTO>> findAllByUserId(@PageableDefault Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.findAllByUserId(pageable, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}