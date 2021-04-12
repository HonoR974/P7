package com.example.service;

import com.example.model.PretDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PretServiceImpl implements PretService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Autowired
    private SecurityService securityService;

    private String jwt;

    private List<PretDTO> listPretRetard;

    private List<PretDTO> listePretEnCours;

    @Autowired
    private SendGrid sendGrid;

    @Value("${templateId}")
    private String EMAIL_TEMPLATE_ID;


    @Override
    public List<PretDTO> getPretRetard() throws IOException, InterruptedException {

        this.jwt = securityService.authticate();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/retards"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


        ObjectMapper mapper = new ObjectMapper();

        listPretRetard = mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

        return mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});
    }

    @Override
    public void sendMailRetard()
    {

        Email from = new Email("honore.guillaudeau1@gmail.com");
        String subject = "Salut toi ";
        Email to = null;


        for(int i = 0; i<= listPretRetard.size() -1 ; i++)
        {

            if( !listPretRetard.get(i).getEnvoieEmail() )
            {
                to = new Email(listPretRetard.get(i).getEmail());
                Content content = new Content("text/html", "I'm replacing the <strong>body tag</strong>" );


                Mail mail = new Mail(from, subject, to, content);
                mail.setTemplateId(EMAIL_TEMPLATE_ID);

                Request request = new Request();
                Response response = null;

                try {
                    request.setMethod(Method.POST);
                    request.setEndpoint("mail/send");
                    request.setBody(mail.build());

                    response = sendGrid.api(request);

                    System.out.println(response.getStatusCode());
                    System.out.println(response.getBody());
                    System.out.println(response.getHeaders());
                } catch (IOException ex) {
                    System.out.println("\n l'envoi a fail ");
                }

                listPretRetard.get(i).setEnvoieEmail(true);
            }

        }
    }

    @Override
    public List<PretDTO> getPretEnCours() throws IOException, InterruptedException {

        this.jwt = securityService.authticate();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/encours"))
                .GET()
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);

        ObjectMapper mapper = new ObjectMapper();

        listePretEnCours = mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

        return mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

    }


    @Override
    public void sendPret() throws IOException, InterruptedException {
        if (listePretEnCours.isEmpty())
        {
            System.out.println("\n il n'y a pas de pret a envoyer ");
        }

        LocalDate date = LocalDate.now();
        LocalDate dateFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String statut = "A Rendre";

        List<PretDTO> listeAEnvoyer =  new ArrayList<>();
        for (PretDTO pretDTO : listePretEnCours)
        {
            dateFin = LocalDate.parse(pretDTO.getDate_fin(),formatter);


            System.out.println("\n la date de fin du pret " + dateFin);

            //si aujourd'hui est la date de fin du pret - 1 semaine
            //alors c'est le moment de le rendre
            System.out.println("\n si la date est " + date.minusDays(7));
            if (date.isAfter(dateFin.minusDays(7)) || date.isEqual(dateFin.minusDays(7)))
            {
                System.out.println("\n faut le rendre ");
                pretDTO.setStatut(statut);
            }

            listeAEnvoyer.add(pretDTO);

        }

        sendList(listeAEnvoyer);

    }

    public void sendList(List<PretDTO> list) throws IOException, InterruptedException {

        System.out.println("\n on check le jwt " + jwt);

        //params a envoyer
        Map<Integer,PretDTO> parameters = new HashMap<>();
        int i = 0 ;
        for (PretDTO pretDTO : list)
        {
            parameters.put(i, pretDTO);
            i++;
        }
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(parameters);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/batch/encours"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody ))
                .setHeader(HttpHeaders.CONTENT_TYPE,"application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String reponse = response.body();

        System.out.println("\n response : " +  response + "\n reponse : " + reponse);


        ObjectMapper mapper = new ObjectMapper();

       // listePretEnCours = mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

      //  return mapper.readValue(response.body().toString(), new TypeReference<List<PretDTO>>() {});

    }
}
