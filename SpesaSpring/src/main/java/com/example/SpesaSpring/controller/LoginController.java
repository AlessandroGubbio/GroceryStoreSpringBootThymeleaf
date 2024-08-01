package com.example.SpesaSpring.controller;

import com.example.SpesaSpring.entities.Utente;
import com.example.SpesaSpring.service.utente.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UtenteService utenteService;

    @GetMapping("")
    public String login(){
        return "index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") Utente user, Model model, HttpSession session){
        if(utenteService.findByUsernameAndPassword(user.getUsername(), user.getPassword()) == null){
            model.addAttribute("error", "There was an error with your credentials");
            return "index";
        }
        session.setAttribute("username", user.getUsername());
        return "redirect:/store";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        session.invalidate();
        return "index";
    }

}
