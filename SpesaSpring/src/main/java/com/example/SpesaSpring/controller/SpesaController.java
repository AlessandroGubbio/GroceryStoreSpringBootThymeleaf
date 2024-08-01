package com.example.SpesaSpring.controller;

import com.example.SpesaSpring.dto.CarrelloDto;
import com.example.SpesaSpring.dto.ProdottoDto;
import com.example.SpesaSpring.dto.UtenteDto;
import com.example.SpesaSpring.service.carrello.CarrelloService;
import com.example.SpesaSpring.service.prodotto.ProdottoService;
import com.example.SpesaSpring.service.utente.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store")
public class SpesaController {

    private static final Logger logger = LoggerFactory.getLogger(SpesaController.class);


    @Autowired
    ProdottoService prodottoService;

    @Autowired
    CarrelloService carrelloService;

    @Autowired
    UtenteService utenteService;

    @GetMapping("")
    public String getStore(HttpSession session, Model model){
        if(session.getAttribute("username") == null){
            return "redirect:/";
        }else{
            List<ProdottoDto> grocery = prodottoService.findAllProdotti();
            model.addAttribute("list", grocery);
            return "spesa";
        }
    }

    @PostMapping("/{itemId}/addCart")
    public String addToCart(@PathVariable long itemId, HttpSession session){
        if(session.getAttribute("username") == null){
            return "redirect:/";
        }
        UtenteDto utente = utenteService.findByUsername((String) session.getAttribute("username"));
        CarrelloDto dto = CarrelloDto.builder()
                .userid(utente.getId())
                .groceryid(itemId)
                .quantity(1)
                .build();
        // Check if the item is already in db (if so add the quantity else put 1 item in the db)
        if(carrelloService.findByUseridAndGroceryid(dto)==null){
            carrelloService.save(dto);
        }else{
        //  if it is not in db add a 1 to the quantity
            carrelloService.increaseItem(dto);
        }
        return "redirect:/store";
    }

    @GetMapping("/{itemId}")
    public String getDetails(@PathVariable("itemId") long id,HttpSession session, Model model){
        if(session.getAttribute("username") == null){
            return "redirect:/";
        }
        ProdottoDto prodottoDto = prodottoService.findById(id);
        model.addAttribute("product", prodottoDto);
        return "dettagli-prodotto";
    }

    @GetMapping("/search")
    public String searchItem(@RequestParam("name") String itemName, @RequestParam("id") long id,  Model model){
        itemName = itemName.substring(0, 1).toUpperCase() + itemName.substring(1);
        try {
            ProdottoDto dto = prodottoService.findByName(itemName);
            return "redirect:/store/" + dto.getId();
        }catch (NullPointerException npe){
            model.addAttribute("error", "Sorry! That item is not available");
            ProdottoDto prodottoDto = prodottoService.findById(id);
            model.addAttribute("product", prodottoDto);
            return "dettagli-prodotto";
        }
    }

}
