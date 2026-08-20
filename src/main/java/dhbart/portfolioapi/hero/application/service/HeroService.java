package dhbart.portfolioapi.hero.application.service;

import dhbart.portfolioapi.hero.application.dto.HeroResponse;
import dhbart.portfolioapi.hero.application.mapper.HeroMapper;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HeroService {

    private static final long HERO_ID = 1L;

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;
    private final LocaleResolver localeResolver;

    @Autowired
    public HeroService(HeroRepository heroRepository, HeroMapper heroMapper, LocaleResolver localeResolver) {
        this.heroRepository = heroRepository;
        this.heroMapper = heroMapper;
        this.localeResolver = localeResolver;
    }

    public HeroService(HeroRepository heroRepository, HeroMapper heroMapper) {
        this(heroRepository, heroMapper, new LocaleResolver());
    }

    public HeroResponse findHero() {
        return heroRepository.findById(HERO_ID)
                .map(heroMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Hero content not found"));
    }

    public HeroResponse findHero(String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var hero = heroRepository.findByLocale(locale);
            if (hero.isPresent()) return heroMapper.toResponse(hero.get());
        }
        throw new ResourceNotFoundException("Hero content not found");
    }
}
