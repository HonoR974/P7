package com.example.service;

import com.example.model.PretDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sendgrid.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
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

    @Value("${sendgrid.emailFrom}")
    private String fromEmail;

    /**
     * Recupere les pret qui sont valider et non fini
     * @return liste pret dto
     * @throws IOException
     * @throws InterruptedException
     */
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

    /** sendPret part-1
     * Une semaine avant la date de fin le statut du pret passe 'A Rendre'
     * @throws IOException
     * @throws InterruptedException
     */
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
            System.out.println("\n la date d'aujourd'hui : "+ date.minusDays(7));

            System.out.println("si la date d'ajourd'hui est comprise dans les 7" +
                    "jours il faut le rendre ");
            if (date.isAfter(dateFin.minusDays(7)) || date.isEqual(dateFin.minusDays(7)))
            {
                System.out.println("\n faut le rendre ");
                pretDTO.setStatut(statut);
            }

            listeAEnvoyer.add(pretDTO);

        }

        sendList(listeAEnvoyer);

    }


    /**
     * sendPret part-1
     *
     * Envoie les prets a rendre
     *
     * @param list
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Les prets verifié sont récupérés
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
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


    /**
     * Envoie les mails pour les pret a rendre
     */
    @Override
    public void sendMailRetard()
    {

        Email from = new Email(fromEmail);
        String subject = "Rappel de votre Bibliotheque";
        Email to = null;


        for(int i = 0; i<= listPretRetard.size() -1 ; i++)
        {

            System.out.println("\n le pret avec l'id " + listPretRetard.get(i).getId() );

            //si le pret n'a pas recu d'email
            if( !listPretRetard.get(i).getEnvoieEmail() )
            {
                to = new Email(listPretRetard.get(i).getEmail());
                Content content = new Content("text/html", "I'm replacing the <strong>body tag</strong>" );

                Content content1 = new Content();

                Mail mail = new Mail(from,subject,to,content);

                mail.setTemplateId(EMAIL_TEMPLATE_ID);

             //   mail.personalization.get(0).addSubstitution("subject", subject);



                System.out.println("\n template id : " + EMAIL_TEMPLATE_ID);
                System.out.println("email corps (from,subject,to,content) : " + from.getEmail() + "   "
                                    + subject + "   " + to + "   " + content);

                Request request = new Request();
                Response response;

                try {
                    request.setMethod(Method.POST);
                    request.setEndpoint("mail/send");
                    request.setBody(mail.build());


                    System.out.println("\n mail " + mail.build());

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

    //fonction qui envoie les prets de sendMailRetard a l'api
    //ceux qui ont recu un email n'en recevront plus



}
