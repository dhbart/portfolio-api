package dhbart.portfolioapi.experience.application.mapper;

import dhbart.portfolioapi.experience.application.dto.ExperienceResponse;
import dhbart.portfolioapi.experience.domain.model.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public ExperienceResponse toResponse(Experience experience) {
        return new ExperienceResponse(
                experience.getId(), experience.getCompany(), experience.getLocation(), experience.getPeriod(),
                experience.getPosition(), experience.getSummary(), experience.getDescription(),
                experience.getHighlights(), experience.getTechnologies(), experience.getStartDate(), experience.getEndDate(),
                experience.getCurrentPosition(), experience.getDisplayOrder());
    }
}
