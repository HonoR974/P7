package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livre", uniqueConstraints = {@UniqueConstraint(columnNames = {"titre"})})
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auteur;

    @Column(name = "titre")
    private String titre;

    @ManyToOne
    private Bibliotheque bibliotheque;

    @OneToMany(mappedBy = "livre")
    private List<Examplaire> examplaires;
}
