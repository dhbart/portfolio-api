package dhbart.portfolioapi.experience.application.dto;

import java.time.LocalDate;

public record ExperienceResponse(
        Long id,
        String company,
        String location,
        String period,
        String position,
        String summary,
        java.util.List<String> description,
        java.util.List<String> highlights,
        java.util.List<String> technologies,
        LocalDate startDate,
        LocalDate endDate,
        Boolean currentPosition,
        Integer displayOrder
) {
}
