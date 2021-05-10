package com.clientui.controller;

import com.clientui.model.PretBean;
import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
import com.clientui.service.ExamplaireService;
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

    @Autowired
    private ExamplaireService examplaireService;

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

        TesterUser user = authBiblioService.testConnection();

        System.out.println("\n user  " + user);

        if (user.isConnected())
        {

            PretDTO pretDTO = pretService.createPret(id_examplaire);

            PretBean pretBean = pretService.givePretBean(pretDTO);

            model.addAttribute("pret", pretBean);
            model.addAttribute("user", authBiblioService.testConnection());
            model.addAttribute("exemplaire", examplaireService.getExamplaire(id_examplaire));
            model.addAttribute("livre", examplaireService.getLivreByIdExamplaire(id_examplaire));

            return "pret/Creation";
        }
        else
        {
            return "redirect:/login";
        }

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
       model.addAttribute("utilisateur", userDTO);
       model.addAttribute("user", authBiblioService.testConnection());

        return "pret/Validate";
    }

    @GetMapping("/finish")
    public String rendreProjet (@RequestParam(value = "id")Long id_pret,
                                Model model) throws IOException, InterruptedException {

        PretDTO pretDTO = pretService.getPretDTOById(id_pret);


        model.addAttribute("pret", pretService.givePretBean(pretDTO));

        return "pret/Finish";
    }

    //seul l'admin peut valider un rendu de pret donc finish pret
    @GetMapping("/validate/finish")
    public String finishPret(@RequestParam(value = "id")Long id,
                             Model model) throws IOException, InterruptedException {

        pretService.finishPret(id);

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
        model.addAttribute("liste",espaceService.getListePretByIdUser(userDTO.getId()));

        return "redirect:/espace";
    }


    @GetMapping("/prolong")
    public String prolongPret(@RequestParam(value = "id")Long id,
                              Model model) throws IOException, InterruptedException {

       PretDTO pretDTO2 = pretService.getPretDTOById(id);
       boolean prolongable=false;
        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());


        //si il n'a pas été prolongé
       if (! pretDTO2.isEnabled())
       {

           PretDTO pretDTO= pretService.prolongPret(id);

           prolongable = true;

           model.addAttribute("user", authBiblioService.testConnection());
           model.addAttribute("utilisateur", userDTO);
           model.addAttribute("liste", espaceService.getListePretByIdUser(userDTO.getId()));
            model.addAttribute("prolongable", prolongable);
       }
       //on peut le prolonger qu'une fois
       else
       {

            prolongable = false;

           model.addAttribute("user", authBiblioService.testConnection());
           model.addAttribute("utilisateur", userDTO);
           model.addAttribute("liste", espaceService.getListePretByIdUser(userDTO.getId()));
           model.addAttribute("prolongable", prolongable);
       }


        return "espace/Accueil";
    }
}
