package com.bibliotheque.service;

import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;

import java.util.List;

public interface LivreService {

    List<Livre> getAllLivres();

    Livre createLivre(Livre livre);

    Livre updateLivre(long id, Livre livre);

    void deleteLivre(long id);

    Livre getLivreById(long id);

    List<Examplaire> getAllExamplaireByIdLivre(long id);

}
