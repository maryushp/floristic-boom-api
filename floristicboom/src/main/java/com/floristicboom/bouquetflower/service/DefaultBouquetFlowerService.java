package com.floristicboom.bouquetflower.service;

import com.floristicboom.bouquetflower.model.BouquetFlower;
import com.floristicboom.bouquetflower.repository.BouquetFlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultBouquetFlowerService implements BouquetFlowerService {
    private final BouquetFlowerRepository bouquetFlowerRepository;

    @Override
    public BouquetFlower create(BouquetFlower bouquetFlower) {
        return bouquetFlowerRepository.save(bouquetFlower);
    }
}
