package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VectorRetrievalService {
    private final RetrievalService retrievalService;

    public VectorRetrievalService(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    public List<KnowledgeChunk> retrieve(String question) {
        return retrievalService.retrieve(question);
    }
}
