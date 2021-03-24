package com.clientui.controller;

import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/espace")
public class EspaceController
{
    @Autowired
    private EspaceService espaceService;

    @Autowired
    private AuthBiblioService authBiblioService;

    /**
     * Accueil espace user
     * @param id
     * @return
     */
    @GetMapping()
    public String espaceAccueil(@RequestParam(name = "id")Long id,
                                Model model) throws IOException, InterruptedException
    {
        model.addAttribute("user", espaceService.getUserDTOByID(id));
        return "espace/Accueil";
    }


    @GetMapping("/prets")
    public String espacePrets(@RequestParam(value = "id")Long id,
                              Model model) throws IOException, InterruptedException
    {
        model.addAttribute("liste", espaceService.getListePretByIdUser(id));

        return "espace/ListePret";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/pret")
    public String pretDetail(@RequestParam(name = "id")Long id,
                             Model model) throws IOException, InterruptedException {
        PretDTO pretDTO = espaceService.getPretDTOByIdPret(id);

        model.addAttribute("pret", pretDTO);


        return "pret/Detail";
    }
}
