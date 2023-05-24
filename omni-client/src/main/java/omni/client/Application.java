package omni.client;

import java.util.Scanner;
import omni.chat.ChatQuery;
import omni.chat.ChatServiceResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        runCommandLineApplication();
    }

    public static void runCommandLineApplication() {
        Scanner scanner = new Scanner(System.in);
        String receivedText = "";
        while (true) {
            System.out.println("Enter a query prompt:");
            receivedText = scanner.nextLine();

            if (!receivedText.equals("exit")) {
                ChatServiceResponse response = issueQuery(new ChatQuery(receivedText));
                System.out.println(response);
            } else {
                break;
            }
        }
    }

    public static ChatServiceResponse issueQuery(ChatQuery query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ChatQuery> httpQueryEntity = new HttpEntity<>(query);
        return restTemplate.postForObject("http://localhost:8081/chat/query", httpQueryEntity, ChatServiceResponse.class);
    }
}
