package com.floristicboom.flower.repository;


import com.floristicboom.flower.model.Flower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

    Page<Flower> findAll(Specification<Flower> spec, Pageable pageable);
}