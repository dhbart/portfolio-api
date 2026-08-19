package dhbart.portfolioapi.project.application.dto;

public record ProjectResponse(
        Long id,
        String slug,
        String title,
        String headline,
        String description,
        String challenge,
        String solution,
        String imageUrl,
        String githubUrl,
        String demoUrl,
        Boolean featured,
        Integer displayOrder
) {
}
