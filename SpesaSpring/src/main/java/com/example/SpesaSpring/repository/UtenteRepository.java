package com.example.SpesaSpring.repository;

import com.example.SpesaSpring.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByUsernameAndPassword(String username, String Password);
    Utente findByUsername(String username);
    Long findIdByUsername(String username);
}
