package dhbart.portfolioapi.assistant.config;

import dhbart.portfolioapi.assistant.retrieval.config.RetrievalProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableConfigurationProperties({AiProperties.class, RetrievalProperties.class, EmbeddingProperties.class,
        AssistantHardeningProperties.class, OpenAiResilienceProperties.class})
public class AssistantConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean(name = "knowledgeProcessingExecutor")
    Executor knowledgeProcessingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("knowledge-processing-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "assistantAiExecutor")
    Executor assistantAiExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(32);
        executor.setThreadNamePrefix("assistant-ai-");
        executor.initialize();
        return executor;
    }
}
