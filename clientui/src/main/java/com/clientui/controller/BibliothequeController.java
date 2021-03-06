package com.clientui.controller;

import com.clientui.beans.BibliothequeBean;
import com.clientui.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;


@Controller
public class BibliothequeController {

    @Autowired
    private BibliothequeService bibliothequeService;


    /**
     * Envoie un jwt a l'API biblio
     * return la liste des bibliotheque de la ville
     * @param jwt
     * @param model
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/bibliotheques")
    public String getAll(@RequestParam String jwt,
                               Model model) throws IOException, InterruptedException {
        List<BibliothequeBean> list = bibliothequeService.getAllBibliotheque(jwt);
        model.addAttribute("list", list);
        return "log/Espace";
    }

    @GetMapping("/bibliotheque/detail")
    public String getBibliothequeById(@RequestParam(name = "id")Long id,
                                      Model model) throws IOException, InterruptedException {

        BibliothequeBean bibliothequeBean = bibliothequeService.getBibliothequeById(id);

        model.addAttribute("biblio", bibliothequeBean);
        return "bibliotheque/Bibliotheque";
    }

    @GetMapping("/bibliotheque/Livres")
    public String getAllLivreByIdBiblio(@RequestParam(value = "id")Long id,
                                        Model model) throws IOException, InterruptedException {
        model.addAttribute("list", bibliothequeService.getAllLivreByIdBiblio(id));

        return "livres/Livres";
    }


}
