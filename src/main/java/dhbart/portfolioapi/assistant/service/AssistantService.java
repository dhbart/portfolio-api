package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private final ChatService chatService;

    public AssistantService(
            ChatService chatService) {
        this.chatService = chatService;
    }

    public ChatResponse chat(String message) {
        return chatService.chat(message);
    }
}
