package com.bibliotheque.web.controller;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.User;
import com.bibliotheque.service.PretService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pret")
public class PretController {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PretService pretService;

    /**
     * Creer un pret avec l'id_examplaire
     * Recois l'id de l'examplaire
     * return un pret
     * @param id_examplaire
     * @return
     */
    @GetMapping("/create/{id}")
    public ResponseEntity<PretDTO> createPret(@PathVariable(name = "id")Long id_examplaire)
    {

        Pret pret = pretService.createPret(id_examplaire);

        PretDTO pretDTO = pretService.givePretDTO(pret);

        return new ResponseEntity<PretDTO>(pretDTO, HttpStatus.ACCEPTED);

    }

    @PostMapping("/validate/{id}")
    public ResponseEntity<PretDTO> validePret(@PathVariable(name = "id")Long id_pret)
    {
        Pret pret = pretService.validePret(id_pret);

        PretDTO pretDTO = pretService.givePretDTO(pret);

        System.out.println("\n apres la validation " + pretDTO.toString());

        return new ResponseEntity<PretDTO>(pretDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PretDTO> getPretById(@PathVariable(name = "id")Long id)
    {
        Pret pret = pretService.getPretById(id);

        PretDTO pretDTO = pretService.givePretDTO(pret);

        return new ResponseEntity<PretDTO>(pretDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping ("/finish/{id}")
    public HttpStatus finishProjet(@PathVariable(value = "id")Long id)
    {
        pretService.finishPret(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/prolong/{id}")
    public ResponseEntity<PretDTO> prolongPret(@PathVariable (value = "id")Long id)
    {
        Pret pret = pretService.prolongPret(id);

        PretDTO pretDTO = pretService.givePretDTO(pret);

        return new ResponseEntity<PretDTO>(pretDTO, HttpStatus.ACCEPTED);
    }
}
