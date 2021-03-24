package com.clientui.service;


import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;



import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthBiblioServiceImpl implements AuthBiblioService{

    private int idUser ;
    private String username;
    private String password;

    private String jwt;

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    @Override
    public String authenticate(UserDTO user) throws IOException, InterruptedException
    {

        //je récupère les valeurs de l'user
        var values = new HashMap<String, String>() {{
            put("username", user.getUsername());
            put ("password", user.getPassword());
        }};


        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/authenticate"))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        //jwt
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());



        String jwt = response.body();

        return jwt;
    }



    @Override
    public String parseJwt(String jwtBrut ) throws JsonProcessingException
    {

        Map<String,Object> result =
                new ObjectMapper().readValue(jwtBrut, HashMap.class);
        Object jwtObject = result.get("jwt");

        this.jwt = jwtObject.toString();
        return jwtObject.toString();

    }


    /**
     * Récupere un User par son token
     * @param jwt jwt
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String getUserNameByToken(String jwt) throws URISyntaxException, IOException, InterruptedException
    {

        //params a envoyer
        Map<String,String> parameters = new HashMap<>();
        parameters.put("jwt",jwt);


        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(parameters);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/user"  ))

                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization","Bearer " +  jwt )

                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        //username
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());


        return response.body();
    }



    @Override
    public UserDTO save(UserDTO userDTO) throws IOException, InterruptedException {


        var values = new HashMap<String, String>() {{
            put("username", userDTO.getUsername());
            put ("password", userDTO.getPassword());
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/register"))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                                          HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        ObjectMapper mapper = new ObjectMapper();

        Map<String, ?> map = mapper.readValue(reponse, Map.class);
        System.out.println("\n \n map " + map + "\n ");


        //attribué les valeurs a la classe
        username = (String) map.get("username");
        password = userDTO.getPassword();


        idUser = Integer.parseInt(String.valueOf(map.get("id")));

        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }


    @Override
    public UserDTO getUserDTOByJwt(String jwt) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/token/" + jwt))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body().toString(), new TypeReference<UserDTO>() {});

    }

    @Override
    public String getJwt()
    {
        return jwt;
    }
}