package com.example.SpesaSpring.controller;

import com.example.SpesaSpring.dto.UtenteDto;
import com.example.SpesaSpring.entities.Utente;
import com.example.SpesaSpring.service.utente.UtenteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterConroller {

    @Autowired
    UtenteService utenteService;

    @GetMapping("")
    private ModelAndView getRegisterPage() {
        Utente user = new Utente();
        return new ModelAndView("register", "user", user);
    }

    @PostMapping("")
    public String register(@Valid @ModelAttribute("user") UtenteDto dto, BindingResult result, HttpSession session){
        if (result.hasErrors()){
            return "register";
        }else{
            session.setAttribute("username", dto.getUsername());
            utenteService.saveUser(dto);
            return "redirect:/store";
        }
    }
}
