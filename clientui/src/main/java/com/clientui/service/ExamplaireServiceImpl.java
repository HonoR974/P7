package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;
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
    public ExamplaireDTO getExamplaire(Long id) throws IOException, InterruptedException {
        this.jwt = livreService.getJwt();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/"+ id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                                    HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

       ExamplaireDTO examplaireDTO = mapper.readValue(response.body().toString(),
                new TypeReference<ExamplaireDTO>() {});

        return examplaireDTO;
    }

    @Override
    public LivreDTO getLivreByIdExamplaire(Long id) throws IOException, InterruptedException {
        this.jwt = livreService.getJwt();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/examplaire/livre/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper= new ObjectMapper();

       LivreDTO livreDTO = mapper.readValue(response.body().toString(),
                new TypeReference<LivreDTO>() {});

        return livreDTO;
    }

    @Override
    public String getJwt()
    {
        return jwt;
    }
}
