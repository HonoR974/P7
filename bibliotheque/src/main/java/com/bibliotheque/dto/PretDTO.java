package com.bibliotheque.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PretDTO
{
    private Long id;
    private String date_debut;
    private String date_fin;

    private String statut;
    private String  username;
    private Long  id_examplaire;
}
