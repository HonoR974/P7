package com.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    /**
     * Return un jwt avec les id du batch
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public String authticate() throws IOException, InterruptedException {

        var values = new HashMap<String, String>() {{
            put("username", "batch");
            put ("password", "batch");
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

        String reponse  = response.body();

        System.out.println("\n response : " + response + "\n reponse : " +reponse);


        String jwt = parseJwt(reponse);


        return jwt;
    }

    public String parseJwt(String jwtBrut ) throws JsonProcessingException
    {

        Map<String,Object> result =
                new ObjectMapper().readValue(jwtBrut, HashMap.class);
        Object jwtObject = result.get("jwt");

        this.jwt = jwtObject.toString();
        return jwtObject.toString();

    }
}
