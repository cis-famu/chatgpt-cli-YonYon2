package dev.yontor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WtfGpt {
     public static void main(String[] args) throws IOException, InterruptedException {
        String prompt;
        if (args.length > 0){
            prompt = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a string to search for: ");
            prompt = scanner.nextLine();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ChatGptRequest chatGptRequest = new ChatGptRequest("text-davinci-001", prompt, 1,100);
        String input = objectMapper.writeValueAsString(chatGptRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Content-Type","application/json")
                .header("Authorization","Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        var response  = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ChatGptResponse chatGptResponse = objectMapper.readValue(response.body(),ChatGptResponse.class);
            //Here I tried to get it to send more than one response but this may not be the way to do it
            String answer1 = chatGptResponse.choices()[chatGptResponse.choices().length-1].text();
            String answer2 = (chatGptResponse.choices().length-2>=0)?chatGptResponse.choices()[chatGptResponse.choices().length-2].text():"";
            System.out.println("answer 1: ");
            if (!answer1.isEmpty()){
                System.out.println(answer1.replace("\n","").trim());
            }
            System.out.println("answer 2: ");
            if (!answer2.isEmpty()){
                System.out.println(answer2.replace("\n","").trim());
            }
        } else {
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
     }
}
