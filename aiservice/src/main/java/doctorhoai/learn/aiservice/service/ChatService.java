package doctorhoai.learn.aiservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private static final String SYSTEM_PROMPT = """
        You are an internal HR assistant. Your role is to help
        employees with questions related to HR policies, such as
        leave policies, working hours, benefits, and code of conduct.
        If a user asks for help with anything outside of these topics,
        kindly inform them that you can only assist with queries related to
        HR policies.
        """;

    private final ChatClient chatClient;

    @Autowired
    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chatWithAI(String userMessage) {
        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(userMessage)
                .call()
                .content();
    }
}

