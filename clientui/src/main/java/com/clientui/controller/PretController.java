package com.clientui.controller;

import com.clientui.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PretController
{

    @Autowired
    private PretService pretService;


    @GetMapping("/pret")
    public String createPret(@RequestParam(value = "id")Long id_examplaire,
                             Model model) throws IOException, InterruptedException
    {

        model.addAttribute("pret", pretService.createPret(id_examplaire));

        return "pret/Creation";
    }
}
