package com.clientui.service;

import com.clientui.beans.BibliothequeBean;
import com.clientui.beans.LivreBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Service
public class BibliothequeServiceImpl implements BibliothequeService{

    private String jwt;

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public List<BibliothequeBean> getAllBibliotheque(String jwtSend ) throws IOException, InterruptedException {
        this.jwt = jwtSend;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/bibliotheque"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtSend)
                .build();

        HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        ObjectMapper mapper = new ObjectMapper();

        List<BibliothequeBean> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<BibliothequeBean>>(){});

        return list;
    }

    @Override
    public BibliothequeBean getBibliothequeById(Long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/bibliotheque/detail?id=" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        ObjectMapper mapper= new ObjectMapper();

       BibliothequeBean bibliothequeBean= mapper.readValue(response.body().toString(),
                new TypeReference<BibliothequeBean>(){});

        return bibliothequeBean;
    }

    @Override
    public List<LivreBean> getAllLivreByIdBiblio(Long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                      .uri(URI.create("http://localhost:9001/api/bibliotheque/Livres?id=" + id))
                      .GET()
                        .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                      .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString() );

        String reponse = response.body();

        ObjectMapper mapper= new ObjectMapper();

        List<LivreBean> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<LivreBean>>() {});

        return list;
    }

    @Override
    public String getJwt()
    {
        return jwt;
    }
}