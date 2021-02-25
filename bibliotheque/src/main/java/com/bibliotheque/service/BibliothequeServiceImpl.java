package com.bibliotheque.service;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.BibliothequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliothequeServiceImpl implements BibliothequeService {

    @Autowired
    private BibliothequeRepository bibliothequeRepository;

    @Override
    public List<Bibliotheque> getAllBibliotheque() {
        return bibliothequeRepository.findAll();
    }

    @Override
    public Bibliotheque createBibliotheque(Bibliotheque bibliotheque) {
        bibliothequeRepository.save(bibliotheque);
        return bibliotheque;
    }

    @Override
    public Bibliotheque updateBibliotheque(long id, Bibliotheque bibliothequeRequest) {
        Bibliotheque bibliotheque = bibliothequeRepository.findById(id);
        bibliotheque.setNom(bibliothequeRequest.getNom());
        bibliotheque.setAdresse(bibliothequeRequest.getAdresse());
        bibliothequeRepository.save(bibliotheque);
        return bibliotheque;
    }

    @Override
    public void deleteBibliotheque(long id) {
        bibliothequeRepository.deleteById(id);
    }

    @Override
    public Bibliotheque getBibliothequeById(long id) {
        Bibliotheque bibliotheque =  bibliothequeRepository.findById(id);
        return bibliotheque;
    }

    @Override
    public List<Livre> getAllLibreByIdBiblio(long id) {
        Bibliotheque bibliotheque = bibliothequeRepository.findById(id);
        return bibliotheque.getLivres();
    }
}
