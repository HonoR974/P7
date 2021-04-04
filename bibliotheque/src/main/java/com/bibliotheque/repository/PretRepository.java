package com.bibliotheque.repository;

import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret,Long> {

    Pret findById(long id);

    List<Pret> findByUserAndStatutOrStatut(User user, Statut statut,Statut statut2);

    List<Pret> findByStatut(Statut statut);
}
