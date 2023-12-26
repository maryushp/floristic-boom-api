package com.floristicboom.bouquet.repository;

import com.floristicboom.bouquet.model.Bouquet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetRepository extends JpaRepository<Bouquet, Long> {

    Page<Bouquet> readBouquetByIsCustomFalse(Pageable pageable);
    Page<Bouquet> findAllByUserId(Pageable pageable, Long userId);
}