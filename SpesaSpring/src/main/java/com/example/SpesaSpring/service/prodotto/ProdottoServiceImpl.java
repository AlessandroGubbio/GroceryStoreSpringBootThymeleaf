package com.example.SpesaSpring.service.prodotto;

import com.example.SpesaSpring.dto.ProdottoDto;
import com.example.SpesaSpring.entities.Prodotto;
import com.example.SpesaSpring.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdottoServiceImpl implements ProdottoService {

    @Autowired
    ProdottoRepository prodottoRepository;

    @Override
    public List<ProdottoDto> findAllProdotti() {
        List<Prodotto> groceries = prodottoRepository.findAll();
        return groceries.stream().map(this::mapToprodottoDto).collect(Collectors.toList());
    }

    @Override
    public ProdottoDto findById(long id) {
        return mapToprodottoDto(prodottoRepository.findById(id));
    }

    @Override
    public ProdottoDto findByName(String itemName) {
        return mapToprodottoDto(prodottoRepository.findByNameContaining(itemName));
    }

    private ProdottoDto mapToprodottoDto(Prodotto prodotto){
        return ProdottoDto.builder()
                .id(prodotto.getId())
                .name(prodotto.getName())
                .description(prodotto.getDescription())
                .price(prodotto.getPrice())
                .photo(prodotto.getPhoto())
                .build();
    }


}
