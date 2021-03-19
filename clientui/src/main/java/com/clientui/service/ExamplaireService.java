package com.clientui.service;

import com.clientui.beans.ExamplaireBean;
import com.clientui.beans.LivreBean;

import java.io.IOException;

public interface ExamplaireService
{
    ExamplaireBean getExamplaire(Long id) throws IOException, InterruptedException;

    LivreBean getLivreByIdExamplaire(Long id) throws IOException, InterruptedException;

    String getJwt();
}
