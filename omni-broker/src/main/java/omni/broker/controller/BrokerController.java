package omni.broker.controller;

import omni.chat.ChatQuery;
import omni.chat.ChatQueryResponse;
import omni.chat.ChatServiceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BrokerController {

    private List<String> newEndpoints = new ArrayList<>();

    @PostMapping(value = "/chat/query", consumes = "application/json")
    public ResponseEntity<ChatServiceResponse> makeChatQuery(@RequestBody ChatQuery chatQuery) {

        List<ChatQueryResponse> responseList = new ArrayList<>();

        for (String serviceUrl: newEndpoints) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<ChatQuery> httpQueryEntity = new HttpEntity<>(chatQuery);
                ChatQueryResponse response = restTemplate.postForObject(serviceUrl+"query", httpQueryEntity, ChatQueryResponse.class);
                responseList.add(response);
            } catch (Exception e) {
                System.out.println("Error in Calling Query Request to Service: " + serviceUrl + "; " + e);
                System.out.println(e);
            }
        }

        ChatServiceResponse serviceResponse = new ChatServiceResponse(chatQuery, responseList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceResponse);
    }

    @PostMapping(value="/chat/image", consumes = "application/json")
    public ResponseEntity<ChatServiceResponse> makeChatImageQuery(@RequestBody ChatQuery chatQuery){
        List<ChatQueryResponse> responseList = new ArrayList<>();
        
        for (String serviceUrl: newEndpoints) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<ChatQuery> httpQueryEntity = new HttpEntity<>(chatQuery);
                ChatQueryResponse response = restTemplate.postForObject(serviceUrl+"image", httpQueryEntity, ChatQueryResponse.class);
                System.out.println("Response from " + serviceUrl + ": " + response);
                responseList.add(response);
            } catch (Exception e) {
                System.out.println("Error in Calling Query Request to Service: " + serviceUrl + "; " + e);
                System.out.println(e);
            }
        }

        ChatServiceResponse serviceResponse = new ChatServiceResponse(chatQuery, responseList);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceResponse);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        System.out.println("Received Test Request");
        return new ResponseEntity<String>("Received", HttpStatus.OK);
    }

    @PostMapping(value="/chatbots", consumes = "text/plain")
    public ResponseEntity<String> addService(@RequestBody String endpoint) {
        for(String service : newEndpoints) {
            if(service.equals(endpoint)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Service already exists");
            }
        }
        newEndpoints.add(endpoint);
        return ResponseEntity.status(HttpStatus.CREATED).body("Service added: " + endpoint);
    }

    @GetMapping(value="/chatbots", produces = "application/json")
    public ResponseEntity<ArrayList<String>> getServices() {
        ArrayList<String> list = new ArrayList<>();
        for(String service : newEndpoints) {
            list.add(service);
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
