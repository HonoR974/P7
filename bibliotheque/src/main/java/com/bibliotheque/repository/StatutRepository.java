package com.bibliotheque.repository;

import com.bibliotheque.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut,Long> {

    Statut findByNom(String nom);
}
