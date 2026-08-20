package dhbart.portfolioapi.support.icons.provider;

import dhbart.portfolioapi.technology.domain.model.Technology;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DeviconProvider implements IconProvider {
    private static final String DEVICON_URL =
            "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/%s/%s-original.svg";
    private static final Map<String, String> DEVICON_ALIASES = Map.of(
            "java-21", "java", "spring-boot", "spring", "spring-ai", "spring", "openapi", "swagger");
    private static final Set<String> UNSUPPORTED = Set.of(
            "tool-calling", "speech-to-text", "text-to-speech", "domain-driven-design",
            "rest-api", "design-patterns", "tmdb-api");

    @Override
    public Optional<URI> resolve(Technology technology) {
        if (UNSUPPORTED.contains(technology.getSlug())) {
            return Optional.empty();
        }
        var deviconSlug = DEVICON_ALIASES.getOrDefault(technology.getSlug(), technology.getSlug());
        return Optional.of(URI.create(DEVICON_URL.formatted(deviconSlug, deviconSlug)));
    }
}
