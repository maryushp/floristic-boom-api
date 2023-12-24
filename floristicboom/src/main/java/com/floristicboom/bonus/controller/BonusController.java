package com.floristicboom.bonus.controller;

import com.floristicboom.bonus.model.BonusDTO;
import com.floristicboom.bonus.service.BonusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bonus")
@RequiredArgsConstructor
public class BonusController {
    private final BonusService bonusService;

    @PostMapping
    public ResponseEntity<BonusDTO> create (@RequestBody @Valid BonusDTO bonusDTO) {
        return ResponseEntity.ok(bonusService.create(bonusDTO));
    }

    @GetMapping
    public ResponseEntity<Page<BonusDTO>> readAll (@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(bonusService.readAll(pageable));
    }

    @GetMapping("/code")
    public ResponseEntity<BonusDTO> findByPromoCode (@RequestParam("promo_code") String promoCode) {
        return ResponseEntity.ok(bonusService.findByPromoCode(promoCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BonusDTO> findById (@PathVariable Long id) {
        return ResponseEntity.ok(bonusService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        bonusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}