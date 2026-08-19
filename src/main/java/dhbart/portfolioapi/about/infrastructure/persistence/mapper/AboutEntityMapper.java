package dhbart.portfolioapi.about.infrastructure.persistence.mapper;

import dhbart.portfolioapi.about.domain.model.About;
import dhbart.portfolioapi.about.infrastructure.persistence.entity.AboutEntity;
import org.springframework.stereotype.Component;

@Component
public class AboutEntityMapper {

    public About toDomain(AboutEntity entity) {
        return About.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }

    public AboutEntity toEntity(About about) {
        return new AboutEntity(
                about.getId(), about.getTitle(), about.getDescription(), null, null);
    }
}
