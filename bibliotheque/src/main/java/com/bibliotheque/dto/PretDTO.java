package com.bibliotheque.dto;

import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class PretDTO
{
    private Long id;
    private Date date_debut;
    private Date date_fin;

    private String statut;
    private Long  id_user;
    private Long  id_examplaire;
}
