package dhbart.portfolioapi.hero.infrastructure.persistence.mapper;

import dhbart.portfolioapi.hero.domain.model.Hero;
import dhbart.portfolioapi.hero.infrastructure.persistence.entity.HeroEntity;
import org.springframework.stereotype.Component;

@Component
public class HeroEntityMapper {

    public Hero toDomain(HeroEntity entity) {
        return Hero.builder()
                .id(entity.getId())
                .locale(entity.getLocale())
                .greeting(entity.getGreeting())
                .name(entity.getName())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .primaryButtonLabel(entity.getPrimaryButtonLabel())
                .primaryButtonUrl(entity.getPrimaryButtonUrl())
                .secondaryButtonLabel(entity.getSecondaryButtonLabel())
                .secondaryButtonUrl(entity.getSecondaryButtonUrl())
                .build();
    }

    public HeroEntity toEntity(Hero hero) {
        return new HeroEntity(
                hero.getId(), hero.getLocale(), hero.getGreeting(), hero.getName(), hero.getTitle(), hero.getDescription(),
                hero.getPrimaryButtonLabel(), hero.getPrimaryButtonUrl(),
                hero.getSecondaryButtonLabel(), hero.getSecondaryButtonUrl(), null, null);
    }
}
