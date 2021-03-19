package com.clientui.service;

import com.clientui.beans.PretBean;
import com.clientui.dto.PretDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class PretServiceImpl implements PretService
{
    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private String jwt;

    private ExamplaireService examplaireService;

    public PretServiceImpl(ExamplaireServiceImpl service)
    {
        this.examplaireService = service;
    }

    @Override
    public PretDTO createPret(Long id_examplaire) throws IOException, InterruptedException {
        this.jwt = examplaireService.getJwt();
        System.out.println("\n jwt : " + jwt );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/pret/create/" + id_examplaire))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response = " + response + "\n reponse : " + reponse );

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body().toString(), new TypeReference<PretDTO>() {});
    }

    @Override
    public PretBean givePretBean(PretDTO pretDTO)
    {
        LocalDate date_debut = LocalDate.parse(pretDTO.getDate_debut());
        LocalDate date_fin = LocalDate.parse(pretDTO.getDate_fin());

        PretBean pretBean = new PretBean();

        pretBean.setId(pretDTO.getId());
        pretBean.setDate_debut(date_debut);
        pretBean.setDate_fin(date_fin);
        pretBean.setUsername(pretDTO.getUsername());
        pretBean.setId_examplare(pretDTO.getId_examplaire());
        pretBean.setStatut(pretDTO.getStatut());

        return pretBean;
    }
}
