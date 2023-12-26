package com.floristicboom.delivery.type.repository;

import com.floristicboom.delivery.type.model.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {
}