package dhbart.portfolioapi.assistant.model;

import java.time.Instant;

public record ChatResponse(String response, String model, Instant timestamp) {
}
