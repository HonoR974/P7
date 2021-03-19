package com.clientui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PretDTO {

    private Long id;
    private Date date_debut;
    private Date date_fin;

    private String statut;
    private Long  id_user;
    private Long  id_examplaire;
}
