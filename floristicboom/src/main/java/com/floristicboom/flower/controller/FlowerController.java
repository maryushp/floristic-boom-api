package com.floristicboom.flower.controller;

import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.service.FlowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/flower")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @PostMapping
    public ResponseEntity<FlowerDTO> create(@RequestBody @Valid FlowerDTO flowerDTO) {
        return ResponseEntity.ok(flowerService.create(flowerDTO));
    }

    @GetMapping
    public ResponseEntity<Page<FlowerDTO>> readAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(flowerService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(flowerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flowerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}