package com.clientui.controller;

import com.clientui.beans.LivreBean;
import com.clientui.beans.UserBean;
import com.clientui.dto.UserDTO;
import com.clientui.service.AuthBiblioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;


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
                                Model model) throws JsonProcessingException {

        System.out.println("\n \n post mapping " +
                "\n username : " + userDTO.getUsername() +
                "\n password: " + userDTO.getPassword() +  "\n " );

        String jwtBrut;
        String jwt = null;
        String username = "vide";
        try {
            jwtBrut = authBiblioService.authenticate(userDTO);
            System.out.println("\n jwtBrut : " + jwtBrut);
            jwt = authBiblioService.parseJwt(jwtBrut);
            System.out.println("\n jwt : " + jwt);
           username = authBiblioService.getUserNameByToken(jwt);
            System.out.println("\n username : " + username);

        } catch (IOException e) {
            System.out.println("\n ca ne marche pas " );
        } catch (InterruptedException e) {
            System.out.println("\n ca ne fonctionne pas " );
        } catch (URISyntaxException e) {
            System.out.println("\n Mauvaise URI " );
        }

        model.addAttribute("username", username);
        model.addAttribute("jwt", jwt);
        return "log/presentation";
    }

    @GetMapping("/token")
    public String tokenForUser(@RequestParam String jwt,
                               Model model) throws IOException, InterruptedException {

        UserDTO user = authBiblioService.getUserDTOByToken(jwt);

        model.addAttribute("user", user);
        return "log/Espace";
    }


    @GetMapping("/register")
    public String inscriptionPage(Model model)
    {
        model.addAttribute("user", new UserDTO());

        return "log/inscription";
    }

    @PostMapping("/register")
    public String inscription(@ModelAttribute(value = "user")UserDTO userDTO,
                              Model model) throws IOException, InterruptedException {

        UserDTO user =  authBiblioService.save(userDTO);
        System.out.println("\n \n user : " + user.toString()  );

        String jwtBrut = authBiblioService.authenticate(user);

        System.out.println("\n \n jwtBrut : " + jwtBrut  );
        String jwt = authBiblioService.parseJwt(jwtBrut);

        model.addAttribute("jwt",jwt);
        model.addAttribute("user", user);

        return "log/presentation";
    }
}
