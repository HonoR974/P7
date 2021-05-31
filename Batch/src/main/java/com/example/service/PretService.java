package com.example.service;

import com.example.model.PretDTO;

import java.io.IOException;
import java.util.List;

public interface PretService {


    List<PretDTO> getPretRetard() throws IOException, InterruptedException;

    void sendMailRetard() throws IOException, InterruptedException;

    List<PretDTO> getPretEnCours() throws IOException, InterruptedException;

    void sendPret() throws IOException, InterruptedException;
}
