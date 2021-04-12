package com.clientui.service;

import com.clientui.model.PretBean;
import com.clientui.dto.PretDTO;

import java.io.IOException;

public interface PretService
{

    PretDTO createPret(Long id_examplaire) throws IOException, InterruptedException;

    PretDTO validePret(Long id_pret) throws IOException, InterruptedException;

    PretBean givePretBean(PretDTO pretDTO);

    PretDTO getPretDTOById(Long id_pret) throws IOException, InterruptedException;

    void finishPret(Long id_pret) throws IOException, InterruptedException;

    PretDTO prolongPret(Long id_pret) throws IOException, InterruptedException;


}
