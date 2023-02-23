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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to search for: ");
        String searchString = scanner.nextLine();

        ObjectMapper objectMapper = new ObjectMapper();
        ChatGptRequest chatGptRequest = new ChatGptRequest("text-davinci-001", searchString, 1,100);
        String input = objectMapper.writeValueAsString(chatGptRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Content-Type","application/json")
                .header("Authorization","Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        var response  = client.send(request, HttpResponse.BodyHandlers.ofString());
         System.out.println(response.statusCode());
         System.out.println(response.body());
     }
}
