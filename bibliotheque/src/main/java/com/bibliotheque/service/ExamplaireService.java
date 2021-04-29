package com.bibliotheque.service;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;

import java.util.List;

public interface ExamplaireService {

    List<Examplaire> getAllExamplaire();

    Examplaire createExamplaire(Examplaire examplaire);

    Examplaire updateExamplaire(long id, Examplaire examplaire);

    void deleteExamplaire(long id);

    Examplaire getExamplaireById(long id);

    Livre getLivreById(long id);


    ExamplaireDTO convertExamplaire(Examplaire examplaire);
}
