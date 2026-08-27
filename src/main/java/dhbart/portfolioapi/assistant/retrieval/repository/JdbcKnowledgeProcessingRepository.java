package dhbart.portfolioapi.assistant.retrieval.repository;

import dhbart.portfolioapi.assistant.model.DocumentProcessingStatus;
import dhbart.portfolioapi.assistant.model.KnowledgeDocument;
import dhbart.portfolioapi.assistant.model.UnprocessedChunk;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcKnowledgeProcessingRepository implements KnowledgeProcessingRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcKnowledgeProcessingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public JdbcKnowledgeProcessingRepository(ObjectProvider<JdbcTemplate> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate.getIfAvailable();
    }

    @Override
    public Optional<KnowledgeDocument> findDocument(UUID documentId) {
        return jdbcTemplate.query("SELECT id, status FROM knowledge.documents WHERE id = ?",
                (rs, rowNum) -> new KnowledgeDocument(rs.getObject("id", UUID.class),
                        DocumentProcessingStatus.valueOf(rs.getString("status"))), documentId).stream().findFirst();
    }

    @Override
    public List<UnprocessedChunk> findChunksWithoutEmbedding(UUID documentId) {
        return jdbcTemplate.query("SELECT id, content FROM knowledge.chunks "
                        + "WHERE document_id = ? AND embedding IS NULL ORDER BY chunk_index",
                (rs, rowNum) -> new UnprocessedChunk(rs.getObject("id", UUID.class), rs.getString("content")), documentId);
    }

    @Override
    public void markProcessing(UUID documentId) {
        jdbcTemplate.update("UPDATE knowledge.documents SET status = 'PROCESSING', "
                + "processing_started_at = CURRENT_TIMESTAMP, processing_finished_at = NULL, error_message = NULL "
                + "WHERE id = ?", documentId);
    }

    @Override
    public void saveEmbedding(UUID chunkId, float[] embedding) {
        String vector = "[" + join(embedding) + "]";
        jdbcTemplate.update("UPDATE knowledge.chunks SET embedding = CAST(? AS vector) "
                + "WHERE id = ? AND embedding IS NULL", vector, chunkId);
    }

    @Override
    public void markCompleted(UUID documentId) {
        jdbcTemplate.update("UPDATE knowledge.documents SET status = 'EMBEDDING_COMPLETED', "
                + "processing_finished_at = CURRENT_TIMESTAMP, error_message = NULL WHERE id = ?", documentId);
    }

    @Override
    public void markFailed(UUID documentId, String errorMessage) {
        jdbcTemplate.update("UPDATE knowledge.documents SET status = 'FAILED', "
                + "processing_finished_at = CURRENT_TIMESTAMP, error_message = ? WHERE id = ?", errorMessage, documentId);
    }

    private String join(float[] values) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) result.append(',');
            result.append(values[i]);
        }
        return result.toString();
    }
}
