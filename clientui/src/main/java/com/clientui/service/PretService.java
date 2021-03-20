package com.clientui.service;

import com.clientui.beans.PretBean;
import com.clientui.dto.PretDTO;

import java.io.IOException;

public interface PretService
{

    PretDTO createPret(Long id_examplaire) throws IOException, InterruptedException;

    PretDTO validePret(Long id_pret) throws IOException, InterruptedException;

    PretBean givePretBean(PretDTO pretDTO);


}
