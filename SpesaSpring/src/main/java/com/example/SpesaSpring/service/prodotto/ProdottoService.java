package com.example.SpesaSpring.service.prodotto;

import com.example.SpesaSpring.dto.ProdottoDto;

import java.util.List;

public interface ProdottoService {
    List<ProdottoDto> findAllProdotti();
    ProdottoDto findById(long id);

    ProdottoDto findByName(String itemName);
}
