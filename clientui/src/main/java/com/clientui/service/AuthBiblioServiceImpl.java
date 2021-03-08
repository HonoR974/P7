package com.clientui.service;


import com.clientui.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthBiblioServiceImpl implements AuthBiblioService{

    private final HttpClient client =  HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public String authenticate(UserDTO user) throws IOException, InterruptedException {

        /**
         * HttpClient client = HttpClient.newHttpClient();
         * HttpRequest request = HttpRequest.newBuilder()
         *       .uri(URI.create("http://openjdk.java.net/"))
         *       .build();
         * client.sendAsync(request, BodyHandlers.ofString())
         *       .thenApply(HttpResponse::body)
         *       .thenAccept(System.out::println)
         *       .join();
         *
         * return webClient.post().uri(ADD_EMPLOYEE_V1)
         *                     .syncBody(employee)
         *                     .retrieve()
         *                     .bodyToMono(Employee.class)
         *                     .block();
         *
         *      * HttpRequest request = HttpRequest.newBuilder()
         *      *       .uri(URI.create("http://openjdk.java.net/"))
         *      *       .timeout(Duration.ofMinutes(1))
         *      *       .header("Content-Type", "application/json")
         *      *       .POST(BodyPublishers.ofFile(Paths.get("file.json")))
         *      *       .build()
         *
         *       .POST(BodyPublishers.ofString("{"cle1":"valeur1", "cle2":"valeur2"}"))
         */

        //je récupère les valeurs de l'user
        var values = new HashMap<String, String>() {{
            put("username", user.getUsername());
            put ("password", user.getPassword());
        }};


        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/authenticate"))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        //jwt
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());



        String jwt = response.body();

        Map<String,Object> result =
                new ObjectMapper().readValue(jwt, HashMap.class);

        System.out.println("\n \n resultat du hasmap " + result + "\n ");

        return jwt;
    }

    @Override
    public void test (){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:9001/test"))
                .build();

        try {
            HttpResponse httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n \n" + httpResponse.body().toString() + "\n \n ");
        }catch (IOException io )
        {
            System.out.println("\n \n ca ne marche pas \n ");
        }catch (InterruptedException in)
        {
            System.out.println("\n \n on a etait interrompu \n ");
        }





        /**
         *  CloseableHttpClient client = HttpClients.createDefault();
         *     HttpPost httpPost = new HttpPost("http://www.example.com");
         *
         *     List<NameValuePair> params = new ArrayList<NameValuePair>();
         *     params.add(new BasicNameValuePair("username", "John"));
         *     params.add(new BasicNameValuePair("password", "pass"));
         *     httpPost.setEntity(new UrlEncodedFormEntity(params));
         *
         *     CloseableHttpResponse response = client.execute(httpPost);
         *     assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
         *     client.close();
         */

    }
}
