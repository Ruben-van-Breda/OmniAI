package omni.client;

import omni.chat.ChatQuery;
import omni.chat.ChatServiceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/chat")
    public String chatSumbit(@RequestParam String name, Model model) {

        ChatServiceResponse serviceResponse = issueQuery(new ChatQuery(name));

        model.addAttribute("responses", serviceResponse.queryResponses);
        model.addAttribute("prompt", name);

        return "index";
    }

    @PostMapping("/chat/image")
    public String chatImageSumbit(@RequestParam String name, Model model) {

        ChatServiceResponse serviceResponse = issueImageQuery(new ChatQuery(name));

        model.addAttribute("responses", serviceResponse.queryResponses);
        model.addAttribute("prompt", name);
        model.addAttribute("image", serviceResponse.queryResponses);

        return "index";
    }

    

    public static ChatServiceResponse issueQuery(ChatQuery query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ChatQuery> httpQueryEntity = new HttpEntity<>(query);
        return restTemplate.postForObject("http://broker:8081/chat/query", httpQueryEntity, ChatServiceResponse.class);
    }

    public static ChatServiceResponse issueImageQuery(ChatQuery query) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ChatQuery> httpQueryEntity = new HttpEntity<>(query);
        return restTemplate.postForObject("http://broker:8081/chat/image", httpQueryEntity, ChatServiceResponse.class);
    }
}
