package dhbart.portfolioapi.assistant.retrieval;

import java.util.List;

public interface RetrievalService {

    default List<String> retrieve(String query) {
        throw new UnsupportedOperationException("Knowledge retrieval is not implemented yet");
    }
}
