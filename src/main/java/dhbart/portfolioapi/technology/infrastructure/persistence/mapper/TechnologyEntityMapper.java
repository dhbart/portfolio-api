package dhbart.portfolioapi.technology.infrastructure.persistence.mapper;

import dhbart.portfolioapi.technology.domain.model.Technology;
import dhbart.portfolioapi.technology.infrastructure.persistence.entity.TechnologyEntity;
import org.springframework.stereotype.Component;

@Component
public class TechnologyEntityMapper {

    public Technology toDomain(TechnologyEntity entity) {
        return Technology.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .icon(entity.getIcon())
                .website(entity.getWebsite())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    public TechnologyEntity toEntity(Technology technology) {
        return new TechnologyEntity(technology.getId(), technology.getName(), technology.getSlug(),
                technology.getIcon(), technology.getWebsite(), technology.getDisplayOrder(), null, null);
    }
}
