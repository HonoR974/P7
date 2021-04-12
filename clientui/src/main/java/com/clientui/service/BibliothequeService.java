package com.clientui.service;

import com.clientui.dto.BibliothequeDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;
import java.util.List;

public interface BibliothequeService {


    List<BibliothequeDTO> getAllBibliotheque(String jwt) throws IOException, InterruptedException;

    BibliothequeDTO getBibliothequeById(Long id) throws IOException, InterruptedException;

    List<LivreDTO> getAllLivreByIdBiblio(Long id) throws IOException, InterruptedException;

    String getJwt();
}
