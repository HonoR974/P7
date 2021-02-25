package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.web.exception.LivreIntrouvableException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livre")
public class LivreController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LivreService livreService;

    @GetMapping
    public List<LivreDTO> getAllLivres()
    {
        List<LivreDTO> livreDTOList = livreService.getAllLivres().stream().map(livre -> modelMapper.map(livre,LivreDTO.class))
                .collect(Collectors.toList());

        if(livreDTOList.size() == 0 ) throw new LivreIntrouvableException( "il n' y a pas de livre ");
        return livreDTOList;
    }

    @PostMapping
    public ResponseEntity<LivreDTO> createLivre(@RequestBody LivreDTO livreDTO)
    {
        //conversion dto a entity
        Livre livreRequest = modelMapper.map(livreDTO, Livre.class);

        Livre livre = livreService.createLivre(livreRequest);

        //conversion entity a dto
        LivreDTO livreResponse = modelMapper.map(livre, LivreDTO.class);

        return new ResponseEntity<LivreDTO>(livreResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreDTO> getLivreById(@PathVariable(name = "id") Long id)
    {
        Livre livre = livreService.getLivreById(id);

        if(livre==null) throw new LivreIntrouvableException( "Le produit avec l'id "
                + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        LivreDTO  livreResponse = modelMapper.map(livre, LivreDTO.class);

        return ResponseEntity.ok().body(livreResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreDTO> updateLivre(@PathVariable(name = "id")Long id,
                                                @RequestBody LivreDTO livreDTO)
    {
        //dto a entity
        Livre livreRequest = modelMapper.map(livreDTO, Livre.class);

        Livre livre = livreService.updateLivre(id, livreRequest);
        if(livre==null) throw new LivreIntrouvableException( "Le produit avec l'id "
                + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        //entity a dto
        LivreDTO livreResponse = modelMapper.map(livre, LivreDTO.class);

        return ResponseEntity.ok().body(livreResponse);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteLivre(@PathVariable(name = "id")Long id)
    {
        livreService.deleteLivre(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}/examplaires")
    public List<ExamplaireDTO>  getAllExamplaireByIdLivre(@PathVariable(name = "id")long id)
    {
        List<Examplaire> examplaires = livreService.getAllExamplaireByIdLivre(id);

        List<ExamplaireDTO> examplaireDTOS = examplaires.stream().map(examplaire -> modelMapper.map(examplaire, ExamplaireDTO.class))
                                            .collect(Collectors.toList());
        return examplaireDTOS;
    }


}
