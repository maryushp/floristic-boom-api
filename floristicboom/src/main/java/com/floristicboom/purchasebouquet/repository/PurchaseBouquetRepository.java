package com.floristicboom.purchasebouquet.repository;

import com.floristicboom.purchasebouquet.model.PurchaseBouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseBouquetRepository extends JpaRepository<PurchaseBouquet, Long> {
}