package com.clientui.controller;

import com.clientui.dto.LivreDTO;
import com.clientui.dto.UserDTO;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.BibliothequeService;
import com.clientui.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Controller AuthBibliothequeController
 */
@Controller
public class AuthBibliothequeController {


    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private BibliothequeService bibliothequeService;

    @Autowired
    private LivreService livreService;


    /**
     * Donne la page de login
     * @param model
     * @return page de login
     */
    @GetMapping("/login")
    public String loggin(Model model)
    {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("utilisateur", userDTO);
        model.addAttribute("user", authBiblioService.testConnection());
        return "log/logging";
    }


    /**
     * Authentifie l'user
     * @param userDTO
     * @param model
     * @return page d'accueil
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/authenticate")
    public String authenticate (@ModelAttribute(value = "user")UserDTO userDTO,
                                Model model) throws IOException, InterruptedException {

        String jwtBrut;
        String jwt = null;
        String username = "vide";
        try {
            jwt = authBiblioService.authenticate(userDTO);

            username = authBiblioService.getUserNameByToken(jwt);


        } catch (IOException e) {
            System.out.println("\n ca ne marche pas " );
        } catch (InterruptedException e) {
            System.out.println("\n ca ne fonctionne pas " );
        } catch (URISyntaxException e) {
            System.out.println("\n Mauvaise URI " );
        }

        model.addAttribute("username", username);
        model.addAttribute("jwt", jwt);
        model.addAttribute("user",authBiblioService.testConnection());

        return  "redirect:/";
    }



    /**
     * Page Inscription
     * @param model
     * @return page inscription
     */
    @GetMapping("/register")
    public String inscriptionPage(Model model)
    {
        model.addAttribute("user", new UserDTO());

        return "log/inscription";
    }

    /**
     * Recois un user
     *
     * @param userDTO
     * @param model
     * @return page liste livres
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/register")
    public String inscription(@ModelAttribute(value = "user")UserDTO userDTO,
                              Model model) throws IOException, InterruptedException {


        UserDTO user =  authBiblioService.save(userDTO);


        String jwt = authBiblioService.authenticate(user);

        model.addAttribute("jwt",jwt);
        model.addAttribute("user", authBiblioService.testConnection());

        List<LivreDTO> list = livreService.getAll();
        model.addAttribute("liste", list);


        return  "livres/Livres";
    }




}
