package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.retrieval.HybridRetrievalService;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private final ChatService chatService;
    private final HybridRetrievalService hybridRetrievalService;
    private final AssistantInputValidator inputValidator;

    public AssistantService(
            ChatService chatService, HybridRetrievalService hybridRetrievalService,
            AssistantInputValidator inputValidator) {
        this.chatService = chatService;
        this.hybridRetrievalService = hybridRetrievalService;
        this.inputValidator = inputValidator;
    }

    public ChatResponse chat(String message) {
        inputValidator.validate(message);
        return chatService.chat(message, hybridRetrievalService.retrieve(message));
    }
}
