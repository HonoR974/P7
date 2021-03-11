package com.clientui.service;


import com.clientui.beans.UserBean;
import com.clientui.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface AuthBiblioService {

    /**
     * va retourner un token jwt
     * @param user
     * @return
     */
     String authenticate (UserDTO user) throws IOException, InterruptedException;

     void test() throws IOException, InterruptedException;

     String parseJwt(String jwt ) throws JsonProcessingException;

     String getUserNameByToken(String jwt) throws IOException, InterruptedException, URISyntaxException;

     UserDTO getUserDTOByToken(String jwt) throws IOException, InterruptedException;

     UserDTO save(UserDTO userDTO) throws IOException, InterruptedException;
}