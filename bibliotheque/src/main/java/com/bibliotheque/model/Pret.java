package com.bibliotheque.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pret
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date_debut;
    private Date date_fin;

    private Boolean prolonger;


    @ManyToOne
    private Statut statut;


    @ManyToOne
    private User user;


    @ManyToOne
    private Examplaire examplaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Boolean getProlonger() {
        return prolonger;
    }

    public void setProlonger(Boolean prolonger) {
        this.prolonger = prolonger;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Examplaire getExamplaire() {
        return examplaire;
    }

    public void setExamplaire(Examplaire examplaire) {
        this.examplaire = examplaire;
    }
}
