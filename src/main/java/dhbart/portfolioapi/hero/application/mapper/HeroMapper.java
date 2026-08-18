package dhbart.portfolioapi.hero.application.mapper;

import dhbart.portfolioapi.hero.application.dto.HeroResponse;
import dhbart.portfolioapi.hero.domain.model.Hero;
import org.springframework.stereotype.Component;

@Component
public class HeroMapper {

    public HeroResponse toResponse(Hero hero) {
        return new HeroResponse(
                hero.getId(), hero.getGreeting(), hero.getName(), hero.getTitle(), hero.getDescription(),
                hero.getPrimaryButtonLabel(), hero.getPrimaryButtonUrl(),
                hero.getSecondaryButtonLabel(), hero.getSecondaryButtonUrl());
    }
}
