package com.example.SpesaSpring.service.utente;

import com.example.SpesaSpring.dto.UtenteDto;
import com.example.SpesaSpring.entities.Utente;
import com.example.SpesaSpring.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteServiceImpl implements UtenteService{

    @Autowired
    UtenteRepository repository;

    @Override
    public UtenteDto findByUsernameAndPassword(String username, String password) {
        Utente user = repository.findByUsernameAndPassword(username, password);
        try{
            return mapToUserDto(user);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public Utente registerUser(UtenteDto userDto) {
        Utente user = mapToUser(userDto);
        if(repository.findByUsername(user.getUsername()) == null){
            return repository.save(user);
        }
        return null;
    }

    @Override
    public Long findIdByUsername(String username) {
        Long id = repository.findIdByUsername(username);
        return id;
    }

    @Override
    public UtenteDto findByUsername(String username) {
        return mapToUserDto(repository.findByUsername(username));
    }

    @Override
    public void saveUser(UtenteDto dto) {
        repository.save(mapToUser(dto));
    }


    private UtenteDto mapToUserDto(Utente user) {
        UtenteDto userDto = UtenteDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        return userDto;
    }

    private Utente mapToUser(UtenteDto userDto) {
        Utente user = Utente.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        return user;

    }
}
