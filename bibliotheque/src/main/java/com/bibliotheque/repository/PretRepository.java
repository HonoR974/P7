package com.bibliotheque.repository;

import com.bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PretRepository extends JpaRepository<Pret,Long> {

    Pret findById(long id);
}
