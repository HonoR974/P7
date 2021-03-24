package com.bibliotheque.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String matchingPassword;
    private String email;

    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Pret> listeDePret;



}