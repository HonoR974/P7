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


    @GetMapping("/login")
    public String loggin(Model model)
    {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "log/logging";
    }


    @PostMapping("/authenticate")
    public String authenticate (@ModelAttribute(value = "user")UserDTO userDTO,
                                Model model) throws IOException, InterruptedException {

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


        UserDTO user = authBiblioService.getUserDTOByJwt(jwt);



        model.addAttribute("username", username);
        model.addAttribute("jwt", jwt);
        model.addAttribute("user",user);


        List<LivreDTO> list = livreService.getAll();
        model.addAttribute("liste", list);

        return  "livres/Livres";
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

        List<LivreDTO> list = livreService.getAll();
        model.addAttribute("liste", list);


        return  "livres/Livres";
    }




}
