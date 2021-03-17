package com.clientui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class ClientController {



    @GetMapping("/")
    public String accueil(Model model)
    {
        return "Accueil";
    }


}