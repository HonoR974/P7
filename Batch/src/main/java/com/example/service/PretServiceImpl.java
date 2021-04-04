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
import java.util.List;

@Service
public class PretServiceImpl implements PretService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Autowired
    private SecurityService securityService;

    private String jwt;

    private List<PretDTO> listPretRetard;

    @Autowired
    private SendGrid sendGrid;

    @Value("${templateId}")
    private String EMAIL_TEMPLATE_ID;


    //avant il faut s'authentifier
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
        System.out.println("\n la liste des pret : " + listPretRetard);

        Email from = new Email("honore.guillaudeau1@gmail.com");
        String subject = "Salut toi ";


        for(int i = 0; i<= listPretRetard.size() -1 ; i++)

        {

            Email to = new Email(listPretRetard.get(i).getEmail());
            Content content = new Content("text/html", "I'm replacing the <strong>body tag</strong>" );

            Mail mail = new Mail(from, subject, to, content);

            mail.setReplyTo(new Email("test.reply@gmail.com"));
            //mail.personalization.get(0).addSubstitution("-username-", "Some blog user");
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

        }
    }


    @Override
    public void write()
    {
        System.out.println("\n write ");
    }
}
