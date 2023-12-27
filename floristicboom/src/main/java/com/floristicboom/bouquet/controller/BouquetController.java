package com.floristicboom.bouquet.controller;

import com.floristicboom.bouquet.model.BouquetCreationRequest;
import com.floristicboom.bouquet.model.BouquetDTO;
import com.floristicboom.bouquet.service.BouquetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/bouquet")
@RequiredArgsConstructor
public class BouquetController {
    private final BouquetService bouquetService;

    @PostMapping
    public ResponseEntity<BouquetDTO> create(@RequestBody @Valid BouquetCreationRequest bouquetCreationRequest) {
        BouquetDTO createdBouquet = bouquetService.create(bouquetCreationRequest);
        return ResponseEntity.created(URI.create("/api/v1/bouquet/" + createdBouquet.id())).body(createdBouquet);
    }

    @GetMapping
    public ResponseEntity<Page<BouquetDTO>> readAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(bouquetService.readAll(pageable));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Page<BouquetDTO>> findAllByUserId(@PageableDefault Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.ok(bouquetService.findAllByUserId(pageable, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BouquetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bouquetService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bouquetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}