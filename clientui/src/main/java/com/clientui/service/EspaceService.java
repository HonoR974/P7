package com.clientui.service;

import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public interface EspaceService
{
    UserDTO getUserDTOByID(Long id_user) throws IOException, InterruptedException;

    List<PretDTO> getListePretByIdUser(Long id_user) throws IOException, InterruptedException;

    PretDTO getPretDTOByIdPret(Long id_pret) throws IOException, InterruptedException;


}
