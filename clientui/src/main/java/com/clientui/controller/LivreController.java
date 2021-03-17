package com.clientui.controller;

import com.clientui.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class LivreController {

    @Autowired
    private LivreService livreService;

    @GetMapping(value = "/livre/Examplaires")
    public String getExamplaireByIdLivre(@RequestParam(value = "id")Long id,
                                         Model model) throws IOException, InterruptedException {

        //le livre
        model.addAttribute("livre", livreService.getLivreByIdLivre(id));
        model.addAttribute("list", livreService.getAllExamplaireByIdLivre(id));
        return "examplaire/Examplaires";
    }
}
