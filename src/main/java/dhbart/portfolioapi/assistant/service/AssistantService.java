package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.retrieval.HybridRetrievalService;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private final ChatService chatService;
    private final HybridRetrievalService hybridRetrievalService;

    public AssistantService(
            ChatService chatService, HybridRetrievalService hybridRetrievalService) {
        this.chatService = chatService;
        this.hybridRetrievalService = hybridRetrievalService;
    }

    public ChatResponse chat(String message) {
        return chatService.chat(message, hybridRetrievalService.retrieve(message));
    }
}
