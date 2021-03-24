package com.clientui.controller;

import com.clientui.beans.PretBean;
import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
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

    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private EspaceService espaceService;

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

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
       model.addAttribute("user", userDTO);

        return "pret/Validate";
    }

    @GetMapping("/finish")
    public String rendreProjet (@RequestParam(value = "id")Long id_pret,
                                Model model) throws IOException, InterruptedException {

        PretDTO pretDTO = pretService.getPretDTOById(id_pret);


        model.addAttribute("pret", pretService.givePretBean(pretDTO));

        return "pret/Finish";
    }

    @GetMapping("/validate/finish")
    public String finishPret(@RequestParam(value = "id")Long id,
                             Model model) throws IOException, InterruptedException {

        pretService.finishPret(id);

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
        model.addAttribute("liste",espaceService.getListePretByIdUser(userDTO.getId()));

        return "espace/ListePret";
    }


    @GetMapping("/prolong")
    public String prolongPret(@RequestParam(value = "id")Long id,
                              Model model) throws IOException, InterruptedException {
        PretDTO pretDTO= pretService.prolongPret(id);

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());

        model.addAttribute("pret",pretDTO);
        model.addAttribute("user", userDTO);
        return "pret/Prolonger";
    }
}
