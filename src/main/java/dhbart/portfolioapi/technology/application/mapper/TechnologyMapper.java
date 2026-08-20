package dhbart.portfolioapi.technology.application.mapper;

import dhbart.portfolioapi.technology.application.dto.TechnologyResponse;
import dhbart.portfolioapi.technology.domain.model.Technology;
import org.springframework.stereotype.Component;

@Component
public class TechnologyMapper {

    public TechnologyResponse toResponse(Technology technology) {
        return new TechnologyResponse(
                technology.getId(), technology.getName(), technology.getSlug(),
                technology.getWebsite(), technology.getDisplayOrder());
    }
}
