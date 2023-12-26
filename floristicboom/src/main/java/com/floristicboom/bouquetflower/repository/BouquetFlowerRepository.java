package com.floristicboom.bouquetflower.repository;

import com.floristicboom.bouquetflower.model.BouquetFlower;
import com.floristicboom.bouquetflower.model.BouquetFlowerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BouquetFlowerRepository extends JpaRepository<BouquetFlower, BouquetFlowerPK> {
}