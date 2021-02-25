package com.bibliotheque.service;

import com.bibliotheque.model.Examplaire;

import java.util.List;

public interface ExamplaireService {

    List<Examplaire> getAllExamplaire();

    Examplaire createExamplaire(Examplaire examplaire);

    Examplaire updateExamplaire(long id, Examplaire examplaire);

    void deleteExamplaire(long id);

    Examplaire getExamplaireById(long id);
}
