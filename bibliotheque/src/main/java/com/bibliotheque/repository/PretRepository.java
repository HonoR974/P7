package com.bibliotheque.repository;

import com.bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PretRepository extends JpaRepository<Pret,Long> {

}
