package com.clientui.beans;

public class LivreBean {

    private Long id;
    private String auteur;
    private String titre;


    public LivreBean()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "LivreBean{" +
                "id=" + id +
                ", auteur='" + auteur + '\'' +
                ", titre='" + titre + '\'' +
                '}';
    }
}