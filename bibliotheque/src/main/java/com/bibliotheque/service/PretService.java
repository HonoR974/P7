package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;

public interface PretService {

    Pret createPret(Long id_examplaire);

    PretDTO givePretDTO(Pret pret);

    Pret validePret(long id_pret);

    Pret getPretById(long id_pret);

    void finishPret (long id_pret);

    Pret prolongPret(long id_pret);
}
