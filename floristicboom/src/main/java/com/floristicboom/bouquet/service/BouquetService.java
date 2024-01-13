package com.floristicboom.bouquet.service;

import com.floristicboom.bouquet.model.BouquetCreationRequest;
import com.floristicboom.bouquet.model.BouquetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BouquetService {
    BouquetDTO create(BouquetCreationRequest bouquetCreationRequest);

    Page<BouquetDTO> readAll(Pageable pageable);

    Page<BouquetDTO> findAllByUserId(Pageable pageable, Long userId);

    BouquetDTO findById(Long id);

    void delete(Long id);

    Page<BouquetDTO> searchBouquets(Pageable pageable,
                                    Integer minSize, Integer maxSize,
                                    Integer minPrice, Integer maxPrice,
                                    String partialName);

}