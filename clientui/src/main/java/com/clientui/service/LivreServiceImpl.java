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
    public List<LivreDTO> getAll() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/api/livre"))
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();


        ObjectMapper mapper = new ObjectMapper();

       HttpResponse httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        List<LivreDTO> list = mapper.readValue(httpResponse.body().toString(), new TypeReference<List<LivreDTO>>(){});


        return list;
    }

    @Override
    public List<ExamplaireDTO> getAllExamplaireByIdLivre(Long id) throws IOException, InterruptedException {



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/examplaires?id=" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " + response + "\n reponse " + reponse);


        ObjectMapper mapper= new ObjectMapper();

        List<ExamplaireDTO> list = mapper.readValue(response.body().toString(),
                new TypeReference<List<ExamplaireDTO>>() {});
        return list;
    }

    @Override
    public LivreDTO getLivreByIdLivre(Long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/livre/detail/" + id))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response " +response + "\n reponse " + reponse);

        ObjectMapper mapper= new ObjectMapper();

        LivreDTO livreDTO = mapper.readValue(response.body().toString(),
                new TypeReference<LivreDTO>() {});

        return livreDTO;
    }

    @Override
    public String getJwt() {
        return jwt;
    }
}