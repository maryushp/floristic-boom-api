package com.floristicboom.flower.controller;

import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.service.FlowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/flower")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @PostMapping
    public ResponseEntity<FlowerDTO> create(@RequestBody @Valid FlowerDTO flowerDTO) {
        FlowerDTO createdFlower = flowerService.create(flowerDTO);
        return ResponseEntity.created(URI.create("/api/v1/flower/" + createdFlower.id())).body(createdFlower);
    }

    @GetMapping
    public ResponseEntity<Page<FlowerDTO>> readAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(flowerService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(flowerService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FlowerDTO>> searchFlowers(@PageableDefault Pageable pageable,
                                                         @RequestParam(required = false) Integer minPrice,
                                                         @RequestParam(required = false) Integer maxPrice,
                                                         @RequestParam(required = false) String partialName,
                                                         @RequestParam(required = false) String color) {
        return ResponseEntity.ok(flowerService.searchFlowers(pageable, minPrice, maxPrice, partialName, color));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flowerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}