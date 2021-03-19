package com.clientui.beans;

import java.time.LocalDate;
import java.util.Date;

public class PretBean
{
    private Long id;

    private LocalDate date_debut;
    private LocalDate date_fin;
    private String username;
    private Long  id_examplare;
    private String statut;

    public PretBean()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Long getId_examplare() {
        return id_examplare;
    }

    public void setId_examplare(Long id_examplare) {
        this.id_examplare = id_examplare;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "PretBean{" +
                "id=" + id +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", userBean='" + username + '\'' +
                ", id_examplare=" + id_examplare +
                ", statut='" + statut + '\'' +
                '}';
    }
}
