package dhbart.portfolioapi.technology.application.dto;

public record TechnologyResponse(
        Long id,
        String name,
        String slug,
        String icon,
        String website,
        Integer displayOrder
) {
}
