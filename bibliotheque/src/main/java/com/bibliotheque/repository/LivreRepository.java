package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository <Livre,Long>{

    Livre findById(long id);

    List<Livre> findByTitreIgnoreCaseOrAuteurIgnoreCase(String titre, String auteur);

    List<Livre> findByTitreOrAuteur(String titre, String auteur);

}
