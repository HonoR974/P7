package com.clientui.controller;

import com.clientui.beans.PretBean;
import com.clientui.dto.PretDTO;
import com.clientui.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class PretController
{

    @Autowired
    private PretService pretService;

    /**
     * Creer le pret
     * @param id_examplaire
     * @param model
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/pret")
    public String createPret(@RequestParam(value = "id")Long id_examplaire,
                             Model model) throws IOException, InterruptedException
    {
        PretDTO pretDTO = pretService.createPret(id_examplaire);

        PretBean pretBean = pretService.givePretBean(pretDTO);

        model.addAttribute("pret", pretBean);

        return "pret/Creation";
    }

    /**
     * Valide le pret
     * @param id_pret
     * @param model
     * @return
     */
    @GetMapping("/validate")
    public String validePret(@RequestParam(value = "id")Long id_pret,
                             Model model) throws IOException, InterruptedException {

        PretDTO pretDTO = pretService.validePret(id_pret);
        PretBean pretBean = pretService.givePretBean(pretDTO);
        model.addAttribute("pret", pretBean);
        return "pret/Validate";
    }
}
