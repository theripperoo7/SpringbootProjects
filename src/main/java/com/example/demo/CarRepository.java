package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findByMarkaContaining(String marka, Pageable pageable);

    Page<Car> findByMarkaContainingAndModelContaining(String marka, String model, Pageable pageable);

    @Query("SELECT c FROM Car c WHERE " +
            "LOWER(c.marka) LIKE LOWER(CONCAT('%', :marka, '%')) " +
            "AND LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%')) " +
            "AND c.poczatekProdukcji = :poczatekProdukcji")
    Page<Car> findByMarkaContainingAndModelContainingAndPoczatekProdukcji(
            @Param("marka") String marka,
            @Param("model") String model,
            @Param("poczatekProdukcji") int poczatekProdukcji,
            Pageable pageable
    );
}

