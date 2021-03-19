package com.bibliotheque.service;


import com.bibliotheque.model.User;

public interface SecurityService {

    boolean isAuthenticated();

    User getUser();

    String getUsername();

}
