package com.clientui.controller;

import com.clientui.beans.LivreBean;
import com.clientui.proxies.MicroserviceLivreProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceLivreProxy livreProxy;

    @GetMapping("/")
    public String accueil(Model model){

        List<LivreBean> livres =  livreProxy.getAllLivres();

        model.addAttribute("livres", livres);

        return "Accueil";
    }

    @GetMapping("/{id}")
    public String livreById (@PathVariable(name = "id")long id,
                             Model model){

        LivreBean livre =  livreProxy.getLivreById(id);

        model.addAttribute("livre", livre);

        return "LivreDetail";
    }

}
