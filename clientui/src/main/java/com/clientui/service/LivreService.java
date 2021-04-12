package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;
import java.util.List;

public interface LivreService {

    List<LivreDTO> getAll() throws IOException, InterruptedException;

    List<ExamplaireDTO> getAllExamplaireByIdLivre(Long id) throws IOException, InterruptedException;

    LivreDTO getLivreByIdLivre(Long id) throws IOException, InterruptedException;

    String getJwt();
}
