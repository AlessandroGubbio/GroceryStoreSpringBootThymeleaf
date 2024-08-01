package com.example.SpesaSpring.service.carrello;

import com.example.SpesaSpring.dto.CarrelloDto;
import com.example.SpesaSpring.entities.Carrello;
import com.example.SpesaSpring.repository.CarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrelloServiceImpl implements  CarrelloService{

    @Autowired
    CarrelloRepository carrelloRepository;

    @Override
    public void addItemToCart(CarrelloDto dto) {

    }

    @Override
    public CarrelloDto findByUseridAndGroceryid(CarrelloDto dto) {
        return carrelloRepository.findByUseridAndGroceryid(dto.getUserid(), dto.getGroceryid());
    }

    @Override
    public void save(CarrelloDto dto) {
        carrelloRepository.save(mapToCarrello(dto));
    }

    @Override
    public void increaseItem(CarrelloDto dto) {
        carrelloRepository.increaseQuantityCountByOneForUserid(dto.getUserid(), dto.getGroceryid());
    }

    @Override
    public List<Carrello> findByUserid(long userid) {
        List<CarrelloDto> list = carrelloRepository.findByUseridOrderByGroceryidAsc(userid);
        return list.stream().map(this::mapToCarrello).collect(Collectors.toList());
    }

    @Override
    public void decreaseItem(CarrelloDto dto) {
        carrelloRepository.decreaseItem(dto.getUserid(), dto.getGroceryid());
    }

    @Override
    public void deleteByUseridAndGroceryid(long userid, long groceryid) {
        carrelloRepository.deleteByUseridAndGroceryid(userid, groceryid);
    }

    @Override
    public void deleteByUserid(Long userid) {
        carrelloRepository.deleteByUserid(userid);
    }

    private Carrello mapToCarrello(CarrelloDto carrelloDto) {
        Carrello carrello = Carrello.builder()
                .id(carrelloDto.getId())
                .userid(carrelloDto.getUserid())
                .groceryid(carrelloDto.getGroceryid())
                .quantity(carrelloDto.getQuantity())
                .build();
        return carrello;

    }
}
