package dhbart.portfolioapi.assistant.model;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank String message) {
}
