package com.clientui.service;

import com.clientui.beans.BibliothequeBean;
import com.clientui.beans.LivreBean;

import java.io.IOException;
import java.util.List;

public interface BibliothequeService {


    List<BibliothequeBean> getAllBibliotheque(String jwt) throws IOException, InterruptedException;

    BibliothequeBean getBibliothequeById(Long id) throws IOException, InterruptedException;

    List<LivreBean> getAllLivreByIdBiblio(Long id) throws IOException, InterruptedException;

    String getJwt();
}
