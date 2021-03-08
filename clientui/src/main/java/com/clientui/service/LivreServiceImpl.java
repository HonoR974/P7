package com.clientui.service;

import com.clientui.beans.LivreBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Service
public class LivreServiceImpl implements  LivreService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public List<LivreBean> getAll() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/livre"))
                .header("Accept","application/json")
                .build();


        ObjectMapper mapper = new ObjectMapper();

       HttpResponse httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        List<LivreBean> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreBean>>(){});


        return list;
    }


}
