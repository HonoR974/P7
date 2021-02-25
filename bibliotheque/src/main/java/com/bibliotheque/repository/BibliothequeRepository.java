package com.bibliotheque.repository;

import com.bibliotheque.model.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliothequeRepository extends JpaRepository<Bibliotheque,Long> {

    Bibliotheque findById(long id);
}
