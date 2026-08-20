package dhbart.portfolioapi.technology.application.dto;

public record TechnologyRequest(
        String name,
        String slug,
        String website,
        Integer displayOrder
) {
}
