package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.AssistantHardeningProperties;
import dhbart.portfolioapi.exception.BusinessException;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class AssistantInputValidator {
    private static final Pattern INJECTION = Pattern.compile(
            "(?i)(ignore\\s+(all\\s+)?previous\\s+instructions|reveal\\s+(the\\s+)?(system|hidden)\\s+prompt|pretend\\s+to\\s+be\\s+the\\s+developer|execute\\s+arbitrary\\s+instructions)");
    private final AssistantHardeningProperties properties;

    public AssistantInputValidator(AssistantHardeningProperties properties) {
        this.properties = properties;
    }

    public void validate(String message) {
        if (message == null || message.isBlank()) throw new BusinessException("Message must not be blank");
        if (message.length() > properties.maxMessageLength()) {
            throw new BusinessException("Message exceeds the maximum allowed length");
        }
        if (properties.rejectInjectionAttempts() && INJECTION.matcher(message).find()) {
            throw new BusinessException("The message contains unsupported instructions");
        }
    }
}
