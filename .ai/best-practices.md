# AI Best Practices

## Prompt management

Keep prompts versioned under `src/main/resources/prompts`, load them through the prompt loader, and select them through configuration.

## Retrieval isolation

Future vector search and context assembly must enter through `RetrievalService`. The assistant must not issue ad-hoc database or vector queries.

## Stateless services

V3.1 is stateless. Conversation memory is deferred until retention, privacy, and context limits are defined.

## ConfigurationProperties

Keep model, temperature, token limits, enablement, and prompt selection in typed `@ConfigurationProperties`. Never commit credentials.

## Context window awareness

Future retrieval must bound chunk count and size before sending context to the model. Token budgets must be tested with prompt and context sizes.

## Future streaming

Streaming is deferred. When introduced, preserve the non-streaming contract and define cancellation, timeouts, errors, and partial responses.
