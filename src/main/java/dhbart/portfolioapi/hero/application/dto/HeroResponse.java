package dhbart.portfolioapi.hero.application.dto;

public record HeroResponse(
        Long id,
        String greeting,
        String name,
        String title,
        String description,
        String primaryButtonLabel,
        String primaryButtonUrl,
        String secondaryButtonLabel,
        String secondaryButtonUrl
) {
}
