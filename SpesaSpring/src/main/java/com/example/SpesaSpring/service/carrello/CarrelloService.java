package com.example.SpesaSpring.service.carrello;

import com.example.SpesaSpring.dto.CarrelloDto;
import com.example.SpesaSpring.entities.Carrello;

import java.util.List;

public interface CarrelloService {

    void addItemToCart(CarrelloDto dto);
    CarrelloDto findByUseridAndGroceryid(CarrelloDto dto);
    void save(CarrelloDto dto);
    void increaseItem(CarrelloDto dto);
    List<Carrello> findByUserid(long userid);

    void decreaseItem(CarrelloDto carrelloDto);

    void deleteByUseridAndGroceryid(long userid, long groceryid);

    void deleteByUserid(Long userid);
}
