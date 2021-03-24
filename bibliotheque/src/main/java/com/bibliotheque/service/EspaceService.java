package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.User;

import java.util.List;

public interface EspaceService {
    User getUserById(long id);

    PretDTO givePretDTO(Pret pret);

    List<Pret> getListPretByIdUser(long id_user);

    List<PretDTO> giveListDTO(List<Pret> list);

    Pret getPretById(long id);
}
