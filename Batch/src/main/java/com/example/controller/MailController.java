package com.example.controller;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MailController {

    @Autowired
    private SendGrid sendGrid;

    /**
    @Value("${templateId}")
    private String EMAIL_TEMPLATE_ID;
    */

    @GetMapping("/sendgrid")
    public String sendEmailWithSendGrid() {

        Email from = new Email("honore.guillaudeau1@gmail.com");
        String subject = "Salut toi ";

        //pretDTO.getEmail()
        Email to = new Email("smiirf123@gmail.com");
        Content content = new Content("text/html", "I'm replacing the <strong>body tag</strong>" );

        Mail mail = new Mail(from, subject, to, content);

        mail.setReplyTo(new Email("test.reply@gmail.com"));
        //mail.personalization.get(0).addSubstitution("-username-", "Some blog user");
      //  mail.setTemplateId(EMAIL_TEMPLATE_ID);

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
            System.out.println(ex.getMessage());
        }

        return "email was successfully send";
    }
}