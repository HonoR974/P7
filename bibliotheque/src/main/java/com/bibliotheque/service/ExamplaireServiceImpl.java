package com.bibliotheque.service;

import com.bibliotheque.model.Examplaire;
import com.bibliotheque.repository.ExamplaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamplaireServiceImpl implements ExamplaireService{

    @Autowired
    private ExamplaireRepository examplaireRepository;

    @Override
    public List<Examplaire> getAllExamplaire() {
        return examplaireRepository.findAll();
    }

    @Override
    public Examplaire createExamplaire(Examplaire examplaire) {
        examplaireRepository.save(examplaire);
        return examplaire;
    }

    @Override
    public Examplaire updateExamplaire(long id, Examplaire examplaireRequest) {
        Examplaire examplaire = examplaireRepository.findById(id);
        examplaire.setEdition(examplaireRequest.getEdition());
        examplaireRepository.save(examplaire);
        return examplaire;
    }

    @Override
    public void deleteExamplaire(long id) {
            examplaireRepository.deleteById(id);
    }

    @Override
    public Examplaire getExamplaireById(long id) {
        return examplaireRepository.findById(id);
    }
}
