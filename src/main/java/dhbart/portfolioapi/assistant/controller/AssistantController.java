package dhbart.portfolioapi.assistant.controller;

import dhbart.portfolioapi.assistant.model.ChatRequest;
import dhbart.portfolioapi.assistant.model.ChatResponse;
import dhbart.portfolioapi.assistant.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assistant")
public class AssistantController {

    private final ChatService assistantService;

    public AssistantController(ChatService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(assistantService.chat(request.message()));
    }
}
