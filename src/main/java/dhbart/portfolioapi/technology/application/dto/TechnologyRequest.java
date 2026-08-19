package dhbart.portfolioapi.technology.application.dto;

public record TechnologyRequest(
        String name,
        String slug,
        String icon,
        String website,
        Integer displayOrder
) {
}
