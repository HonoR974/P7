package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
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

    List<LivreDTO> convertListLivre(List<Livre> list);

    LivreDTO convertLivre(Livre livre);

}
