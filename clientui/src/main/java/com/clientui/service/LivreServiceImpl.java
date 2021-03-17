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
import java.util.List;
@Service
public class LivreServiceImpl implements  LivreService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    private BibliothequeService bibliothequeService;

    public LivreServiceImpl(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    @Override
    public List<LivreBean> getAll() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/livre"))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();


        ObjectMapper mapper = new ObjectMapper();

       HttpResponse httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        List<LivreBean> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreBean>>(){});


        return list;
    }

    @Override
    public List<ExamplaireBean> getAllExamplaireByIdLivre(Long id) throws IOException, InterruptedException {
        this.jwt = bibliothequeService.getJwt();

        System.out.println("\n jwt : " + jwt );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/examplaires?id=" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        ObjectMapper mapper= new ObjectMapper();

        List<ExamplaireBean> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<ExamplaireBean>>() {});
        return list;
    }

    @Override
    public LivreBean getLivreByIdLivre(Long id) throws IOException, InterruptedException {

        this.jwt = bibliothequeService.getJwt();
        System.out.println("\n jwt " + jwt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/detail?id=" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());


        String reponse = response.body();

        System.out.println("\n response : " + response + "\n reponse : " + reponse.toString());
        ObjectMapper mapper= new ObjectMapper();

        LivreBean livreBean = mapper.readValue(response.body().toString(),
                new TypeReference<LivreBean>() {});

        return livreBean;
    }
}