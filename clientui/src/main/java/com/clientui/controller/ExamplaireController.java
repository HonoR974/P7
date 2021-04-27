package com.clientui.controller;

import com.clientui.service.ExamplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ExamplaireController {

    @Autowired
    private ExamplaireService examplaireService;



    /**
     * Recois l'id de l'examplaire
     * return l'examplaire,son livre
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/examplaire")
    public String afficheExamplaire(@RequestParam (value = "id")Long id,
                                    Model model) throws IOException, InterruptedException {

        model.addAttribute("examplaire", examplaireService.getExamplaire(id));
        model.addAttribute("livre", examplaireService.getLivreByIdExamplaire(id));

        //fonction test
        // si un user est connecté


        return "examplaire/Detail";
    }
}
