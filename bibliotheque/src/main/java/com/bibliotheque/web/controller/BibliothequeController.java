package com.bibliotheque.web.controller;

import com.bibliotheque.dto.BibliothequeDTO;
import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.BibliothequeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bibliotheque")
public class BibliothequeController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BibliothequeService bibliothequeService;

    @GetMapping
    public List<BibliothequeDTO> getAllBiblio()
    {
        return bibliothequeService.getAllBibliotheque().stream().map(
                bibliotheque -> modelMapper.map(bibliotheque, BibliothequeDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<BibliothequeDTO> createBibliotheque(@RequestBody BibliothequeDTO bibliothequeDTO)
    {
        Bibliotheque biblioRequest = modelMapper.map(bibliothequeDTO, Bibliotheque.class);

        Bibliotheque bibliotheque = bibliothequeService.createBibliotheque(biblioRequest);

        BibliothequeDTO bibliothequeResponse = modelMapper.map(bibliotheque, BibliothequeDTO.class );

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliothequeDTO> getBiBliothequeById(@PathVariable(name = "id")Long id)
    {
        Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(id);

        BibliothequeDTO bibliothequeResponse = modelMapper.map(bibliotheque, BibliothequeDTO.class);

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliothequeDTO> updateBibliotheque(@PathVariable(name = "id")Long id,
                                            @RequestBody BibliothequeDTO bibliothequeDTO )
    {
        Bibliotheque bibliothequeRequest = modelMapper.map(bibliothequeDTO, Bibliotheque.class);

        Bibliotheque bibliotheque = bibliothequeService.updateBibliotheque(id, bibliothequeRequest);

        BibliothequeDTO bibliothequeResponse =modelMapper.map(bibliotheque, BibliothequeDTO.class);

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteBibliotheque(@PathVariable(name = "id")Long id)
    {
        bibliothequeService.deleteBibliotheque(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}/livres")
    public List<LivreDTO> getAllLivresByIdBiblio(@PathVariable(name = "id")Long id)
    {
        List<Livre> livreList= bibliothequeService.getAllLibreByIdBiblio(id);

        List<LivreDTO> livreDTOList = livreList.stream().map(livre -> modelMapper.map(livre, LivreDTO.class))
                                        .collect(Collectors.toList());

        return livreDTOList;
    }
}
