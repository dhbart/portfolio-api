package dhbart.portfolioapi.experience.infrastructure.persistence.mapper;

import dhbart.portfolioapi.experience.domain.model.Experience;
import dhbart.portfolioapi.experience.infrastructure.persistence.entity.ExperienceEntity;
import org.springframework.stereotype.Component;

@Component
public class ExperienceEntityMapper {

    public Experience toDomain(ExperienceEntity entity) {
        return Experience.builder()
                .id(entity.getId())
                .locale(entity.getLocale())
                .company(entity.getCompany())
                .location(entity.getLocation())
                .period(entity.getPeriod())
                .position(entity.getPosition())
                .summary(entity.getSummary())
                .description(entity.getDescription())
                .highlights(entity.getHighlights())
                .technologies(entity.getTechnologies())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .currentPosition(entity.getCurrentPosition())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    public ExperienceEntity toEntity(Experience experience) {
        return new ExperienceEntity(
                experience.getId(), experience.getLocale(), experience.getCompany(), experience.getLocation(), experience.getPeriod(),
                experience.getPosition(), experience.getSummary(), experience.getDescription(),
                experience.getHighlights(), experience.getTechnologies(), experience.getStartDate(), experience.getEndDate(),
                experience.getCurrentPosition(), experience.getDisplayOrder(), null, null);
    }
}
