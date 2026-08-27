package dhbart.portfolioapi.assistant.retrieval.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhbart.portfolioapi.assistant.retrieval.model.KnowledgeChunk;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Repository;

@Repository
public class KnowledgeRetrievalRepository {
    private static final String SQL = """
            SELECT id, document_id, content, metadata, embedding <=> CAST(? AS vector) AS distance
            FROM knowledge.chunks WHERE embedding IS NOT NULL
            ORDER BY embedding <=> CAST(? AS vector) LIMIT ?
            """;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public KnowledgeRetrievalRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public KnowledgeRetrievalRepository(ObjectProvider<JdbcTemplate> jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate.getIfAvailable();
        this.objectMapper = objectMapper;
    }

    public List<KnowledgeChunk> findSimilar(float[] embedding, int topK) {
        if (topK <= 0 || jdbcTemplate == null) return List.of();
        String vector = toVector(embedding);
        return jdbcTemplate.query(SQL, this::mapChunk, vector, vector, topK);
    }

    private KnowledgeChunk mapChunk(ResultSet rs, int rowNum) throws SQLException {
        return new KnowledgeChunk(rs.getString("id"), rs.getString("document_id"), null, null,
                rs.getString("content"), rs.getDouble("distance"), parseMetadata(rs.getString("metadata")));
    }

    private java.util.Map<String, Object> parseMetadata(String value) throws SQLException {
        if (value == null) return Collections.emptyMap();
        try {
            return objectMapper.readValue(value, new TypeReference<>() {});
        } catch (Exception exception) {
            throw new SQLException("Invalid knowledge chunk metadata", exception);
        }
    }

    private String toVector(float[] embedding) {
        StringBuilder vector = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            if (i > 0) vector.append(',');
            vector.append(embedding[i]);
        }
        return vector.append(']').toString();
    }
}
