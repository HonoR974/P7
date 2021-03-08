package com.clientui.service;


import com.clientui.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.http.HttpClient;

public interface AuthBiblioService {

    /**
     * va retourner un token jwt
     * @param user
     * @return
     */
    /**
     * va retourner un token jwt
     * @param user
     * @return
     */
     String authenticate (UserDTO user) throws IOException, InterruptedException;

     void test() throws IOException, InterruptedException;
}
