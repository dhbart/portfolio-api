package dhbart.portfolioapi.assistant.model;

import java.util.UUID;

public record UnprocessedChunk(UUID id, String content) {
}
