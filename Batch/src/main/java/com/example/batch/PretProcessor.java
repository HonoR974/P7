package com.example.batch;

import com.example.model.PretDTO;

import com.example.sengrid.EmailService;
import com.example.service.PretService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PretProcessor implements ItemProcessor<List<PretDTO>,List<String> > {

    @Autowired
    private SendGrid sendGrid;

    @Value("${templateId}")
    private String EMAIL_TEMPLATE_ID;

    @Override
    public List<String> process(List<PretDTO> list) throws Exception {

        System.out.println("\n pret dans le process : " + list.toString());

        List<String> listEtat = new ArrayList<>();

        try {

            Email from = new Email("honore.guillaudeau1@gmail.com");
            String subject = "Salut toi ";

            int i = 0;
            while (i <= list.size())
            {
                //pretDTO.getEmail()
                Email to = new Email(list.get(i).getEmail());
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

                listEtat.add(list.get(i).getUsername());

                i++;
            }
        }catch (Exception e)
        {
            System.out.println("\n ca ne fonctionne pas ");
        }

        return listEtat;
    }
}
