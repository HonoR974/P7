package com.clientui.controller;

import com.clientui.beans.LivreBean;
import com.clientui.dto.UserDTO;
import com.clientui.service.AuthBiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


@Controller
public class AuthBibliothequeController {

    @Autowired
    private AuthBiblioService authBiblioService;


    /**
     * methode pour s'authentifier
     *
     * on envoie  la methode avec HttpClient
     * pour recevoir le jeton
     *
     * ------------
     * Verification des attributs de User
     *  Envoyer la requete Http a API Biblio
     *  on recoit
     *
     */

    @GetMapping("/test")
    public void test(@RequestBody LivreBean livreBean,
            Model model) throws IOException, InterruptedException {
        authBiblioService.test();

    }

    @GetMapping("/login")
    public String loggin(Model model)
    {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "log/logging";
    }


    @PostMapping("/authenticate")
    public String authenticate (@ModelAttribute(value = "user")UserDTO userDTO,
                                Model model)  {
        System.out.println("\n \n post mapping " +
                "\n username : " + userDTO.getUsername() +
                "\n password: " + userDTO.getPassword() );

        String jwt = null;
        try {
            jwt = authBiblioService.authenticate(userDTO);
        } catch (IOException e) {
            System.out.println("\n ca ne marche pas " );
        } catch (InterruptedException e) {
            System.out.println("\n ca ne fonctionne pas " );
        }


        model.addAttribute("jwt", jwt);
        return "log/presentation";
    }



}
