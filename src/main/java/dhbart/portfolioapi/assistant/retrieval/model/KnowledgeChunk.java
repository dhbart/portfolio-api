package dhbart.portfolioapi.assistant.retrieval.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A knowledge chunk indexed by the external knowledge pipeline.
 *
 * <p>The model deliberately contains no persistence or vector-store metadata.
 * It is the contract consumed by the retrieval and assistant layers.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeChunk {

    private String id;
    private String documentId;
    private String title;
    private String language;
    private String content;
    private Double similarityScore;
    private Map<String, Object> metadata;
}
