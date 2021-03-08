package com.clientui.controller;

import com.clientui.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class LivreController {

    @Autowired
    private LivreService livreService;


    @GetMapping("/")
    public String accueil(Model model) throws IOException, InterruptedException {
        model.addAttribute("livres", livreService.getAll());
        return "Accueil";
    }
}
