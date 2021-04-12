package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.Pret;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BatchService {

    List<PretBatchDTO> getPretRetard();

    List<PretBatchDTO> getPretEnCours() throws IOException;

    void sendPretEnCours(Map<Integer,PretBatchDTO> map);


}
