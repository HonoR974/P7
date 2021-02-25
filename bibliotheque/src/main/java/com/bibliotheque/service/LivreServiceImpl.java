package com.bibliotheque.service;

import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.web.exception.LivreIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreServiceImpl implements LivreService{

    @Autowired
    private LivreRepository livreRepository;


    @Override
    public List<Livre> getAllLivres()
    {
        return livreRepository.findAll();
    }

    @Override
    public Livre createLivre(Livre livre)
    {
        livreRepository.save(livre);
        return livre;
    }

    @Override
    public Livre getLivreById(long id) {
        Livre livre=  livreRepository.findById(id);

        return livre;
    }

    @Override
    public Livre updateLivre(long id, Livre livreRequest)
    {
        Livre livre = livreRepository.findById(id);
        livre.setTitre(livreRequest.getTitre());
        livre.setAuteur(livreRequest.getAuteur());
        livreRepository.save(livre);
        return livre;
    }

    @Override
    public void deleteLivre(long id) {
        livreRepository.deleteById(id);
    }

    @Override
    public List<Examplaire> getAllExamplaireByIdLivre(long id) {
        Livre livre = livreRepository.findById(id);
        return livre.getExamplaires();
    }
}
