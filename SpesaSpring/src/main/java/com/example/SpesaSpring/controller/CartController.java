package com.example.SpesaSpring.controller;

import com.example.SpesaSpring.dto.CarrelloDto;
import com.example.SpesaSpring.dto.CarrelloInfoDto;
import com.example.SpesaSpring.dto.ProdottoDto;
import com.example.SpesaSpring.dto.UtenteDto;
import com.example.SpesaSpring.entities.Carrello;
import com.example.SpesaSpring.service.carrello.CarrelloService;
import com.example.SpesaSpring.service.prodotto.ProdottoService;
import com.example.SpesaSpring.service.utente.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CarrelloService carrelloService;

    @Autowired
    UtenteService utenteService;

    @Autowired
    ProdottoService prodottoService;

    @GetMapping("")
    public String getCart(HttpSession session, Model model){
        if(session.getAttribute("username")==null){
            return "redirect:/";
        }
        UtenteDto utente = utenteService.findByUsername((String) session.getAttribute("username"));
        // list of grocerid and their quantity
        List<Carrello> list = carrelloService.findByUserid(utente.getId());
        // get the info for each grocery id
        List<CarrelloInfoDto> infoDtoList = new ArrayList<>();
        for (Carrello carrello : list) {
            // product for id
            ProdottoDto prodotto = prodottoService.findById(carrello.getGroceryid());
            CarrelloInfoDto dto = CarrelloInfoDto.builder()
                            // groceryid as the id of InfoDto
                            .id(prodotto.getId())
                            .name(prodotto.getName())
                            .photo(prodotto.getPhoto())
                            .price(prodotto.getPrice())
                            .quantity(carrello.getQuantity())
                            .total(Math.round(prodotto.getPrice() * carrello.getQuantity()))
                            .build();
            if(dto.getQuantity()<1){
                carrelloService.deleteByUseridAndGroceryid(carrello.getUserid(), carrello.getGroceryid());
            }else{
                infoDtoList.add(dto);
            }
        }
        model.addAttribute("list", infoDtoList);
        model.addAttribute("username", session.getAttribute("username"));
        return "carrello";
    }

    @PostMapping("/{itemId}")
    public String addOrRemoveItem( @PathVariable long itemId, @RequestParam String action, HttpSession session){
        CarrelloDto carrelloDto = CarrelloDto.builder()
                .userid(utenteService.findByUsername((String) session.getAttribute("username")).getId())
                .groceryid(itemId)
                .build();
        if(Objects.equals(action, "Add")){
            carrelloService.increaseItem(carrelloDto);
        }else{
            carrelloService.decreaseItem(carrelloDto);
        }
        return "redirect:/cart";
    }

    @GetMapping("/buyitems")
    public String buyItems(HttpSession session, Model model){
        if(session.getAttribute("username")==null){
            return "redirect:/";
        }
        UtenteDto utente = utenteService.findByUsername((String) session.getAttribute("username"));
        List<Carrello> list = carrelloService.findByUserid(utente.getId());
        if(list.isEmpty()){
            model.addAttribute("empty", "Your cart is currently empty, please add an item to " +
                    "the cart before purchasing");
            return "carrello";
        }else{
            carrelloService.deleteByUserid(utenteService.findByUsername((String)
                    session.getAttribute("username")).getId());
            model.addAttribute("thanks", "Thank you for your purchase");
            return "carrello";
        }

    }

}
