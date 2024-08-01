package com.example.SpesaSpring.service.utente;

import com.example.SpesaSpring.dto.UtenteDto;
import com.example.SpesaSpring.entities.Utente;

public interface UtenteService {
    UtenteDto findByUsernameAndPassword(String username, String password);
    Utente registerUser(UtenteDto userDto);
    Long findIdByUsername(String username);
    UtenteDto findByUsername(String username);

    void saveUser(UtenteDto dto);
}
