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

        String jwtBrut;
        String jwt = null;
        String username = "vide";
        try {
            jwtBrut = authBiblioService.authenticate(userDTO);

            jwt = authBiblioService.parseJwt(jwtBrut);
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
        return "log/presentation";
    }



    /**
     * Page Register
     * @param model
     * @return
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
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/register")
    public String inscription(@ModelAttribute(value = "user")UserDTO userDTO,
                              Model model) throws IOException, InterruptedException {

        UserDTO user =  authBiblioService.save(userDTO);

        String jwtBrut = authBiblioService.authenticate(user);

        String jwt = authBiblioService.parseJwt(jwtBrut);

        model.addAttribute("jwt",jwt);
        model.addAttribute("user", user);

        return "log/presentation";
    }




}
