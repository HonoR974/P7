package com.clientui.service;

import com.clientui.beans.ExamplaireBean;
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

@Service
public class ExamplaireServiceImpl implements ExamplaireService
{
    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private LivreService livreService;

    private String jwt;

    public ExamplaireServiceImpl(LivreService livreSer)
    {
        this.livreService = livreSer;
    }

    /**
     * return l'examplaire par son id
     * @param id
     * @return
     */
    @Override
    public ExamplaireBean getExamplaire(Long id) throws IOException, InterruptedException {
        this.jwt = livreService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/"+ id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                                    HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper= new ObjectMapper();

       ExamplaireBean examplaireBean= mapper.readValue(response.body().toString(),
                new TypeReference<ExamplaireBean>() {});

        return examplaireBean;
    }

    @Override
    public LivreBean getLivreByIdExamplaire(Long id) throws IOException, InterruptedException {
        this.jwt = livreService.getJwt();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/livre/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper= new ObjectMapper();

       LivreBean livreBean = mapper.readValue(response.body().toString(),
                new TypeReference<LivreBean>() {});

        return livreBean;
    }

    @Override
    public String getJwt()
    {
        return jwt;
    }
}
