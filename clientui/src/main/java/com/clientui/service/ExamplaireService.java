package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;

public interface ExamplaireService
{
    ExamplaireDTO getExamplaire(Long id) throws IOException, InterruptedException;

    LivreDTO getLivreByIdExamplaire(Long id) throws IOException, InterruptedException;

    String getJwt();
}
