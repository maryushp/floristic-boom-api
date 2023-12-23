package com.floristicboom.flower.controller;

import com.floristicboom.flower.model.FlowerDTO;
import com.floristicboom.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/flower")
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @GetMapping
    public ResponseEntity<Page<FlowerDTO>> readAll(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(flowerService.getAll(pageable));
    }
}