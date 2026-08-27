package dhbart.portfolioapi.assistant.controller;

import dhbart.portfolioapi.assistant.model.ProcessingResponse;
import dhbart.portfolioapi.assistant.service.KnowledgeProcessingService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/knowledge")
public class KnowledgeProcessingController {

    private final KnowledgeProcessingService service;

    public KnowledgeProcessingController(KnowledgeProcessingService service) {
        this.service = service;
    }

    @PostMapping("/process/{documentId}")
    public ResponseEntity<ProcessingResponse> process(@PathVariable UUID documentId) {
        service.process(documentId);
        return ResponseEntity.accepted().body(new ProcessingResponse("processing"));
    }
}
