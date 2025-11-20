package doctorhoai.learn.aiservice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatClient chatClient;
    public ChatController(@Qualifier("chatMemoryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource promptTemplate;

    @GetMapping("/chat")
    public Map<String, List<String>> chat(@RequestParam("message") String message) {
        Map<String, List<String>> countryMapList = this.chatClient
                .prompt().user(
                        promptTemplate -> promptTemplate.text(message)

                )
                .call()
                .entity(new ParameterizedTypeReference<Map<String, List<String>>>(){});
        return countryMapList;
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam("message") String message) {
        return chatClient.prompt()
            .user(message)
            .stream()
            .content();
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<?> chatMemory(@RequestHeader("username") String username,
                                             @RequestParam("message") String message) {
        Map<String, Object> countryCities = chatClient
                .prompt()
                .advisors(
                        advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)
                )
                .user(message)
                .call().entity(new MapOutputConverter());
        return ResponseEntity.ok(countryCities);
    }
}
