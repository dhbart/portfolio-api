package dhbart.portfolioapi.assistant.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dhbart.portfolioapi.assistant.service.KnowledgeProcessingService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class KnowledgeProcessingControllerTest {

    private final KnowledgeProcessingService service = mock(KnowledgeProcessingService.class);
    private final MockMvc mvc = MockMvcBuilders.standaloneSetup(new KnowledgeProcessingController(service)).build();

    @Test
    void returnsAcceptedProcessingResponse() throws Exception {
        UUID documentId = UUID.randomUUID();

        mvc.perform(post("/api/v1/admin/knowledge/process/{documentId}", documentId))
                .andExpect(status().isAccepted())
                .andExpect(content().json("{\"status\":\"processing\"}"));

        verify(service).process(documentId);
    }
}
