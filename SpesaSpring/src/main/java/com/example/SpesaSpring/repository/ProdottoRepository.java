package com.example.SpesaSpring.repository;

import com.example.SpesaSpring.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    Prodotto findById(long id);

    Prodotto findByNameContaining(String itemName);
}
