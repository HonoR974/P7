package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;

public interface PretService {

    Pret createPret(Long id_examplaire);

    PretDTO givePretDTO(Pret pret);
}
