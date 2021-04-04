package com.example.service;

import com.example.model.PretDTO;

import java.io.IOException;
import java.util.List;

public interface PretService {


    List<PretDTO> getPretRetard() throws IOException, InterruptedException;

    List<PretDTO> sendMailRetard();

    void write();
}
