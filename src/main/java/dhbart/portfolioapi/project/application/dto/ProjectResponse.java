package dhbart.portfolioapi.project.application.dto;

import dhbart.portfolioapi.technology.application.dto.TechnologyResponse;
import java.util.List;

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
        Integer displayOrder,
        List<TechnologyResponse> technologies
) {
}
