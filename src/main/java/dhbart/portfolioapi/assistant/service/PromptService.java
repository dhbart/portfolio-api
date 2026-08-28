package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.AiProperties;
import dhbart.portfolioapi.assistant.prompt.PromptBuilder;
import dhbart.portfolioapi.assistant.prompt.PromptLoader;
import dhbart.portfolioapi.assistant.retrieval.model.Context;
import dhbart.portfolioapi.assistant.config.AssistantHardeningProperties;
import dhbart.portfolioapi.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
    private final PromptLoader loader;
    private final PromptBuilder builder;
    private final AiProperties properties;
    private final AssistantHardeningProperties hardeningProperties;

    public PromptService(PromptLoader loader, PromptBuilder builder, AiProperties properties,
                         AssistantHardeningProperties hardeningProperties) {
        this.loader = loader;
        this.builder = builder;
        this.properties = properties;
        this.hardeningProperties = hardeningProperties;
    }

    public String build(Context context, String question, int maxContextLength) {
        String prompt = builder.build(loader.load(properties.systemPrompt()), context, question, maxContextLength);
        if (prompt.length() > hardeningProperties.maxPromptLength()) {
            throw new BusinessException("The generated prompt exceeds the maximum allowed length");
        }
        return prompt;
    }
}
