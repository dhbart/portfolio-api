package dhbart.portfolioapi.hero.application.service;

import dhbart.portfolioapi.hero.application.dto.HeroResponse;
import dhbart.portfolioapi.hero.application.mapper.HeroMapper;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HeroService {

    private static final long HERO_ID = 1L;

    private final HeroRepository heroRepository;
    private final HeroMapper heroMapper;

    public HeroService(HeroRepository heroRepository, HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.heroMapper = heroMapper;
    }

    public HeroResponse findHero() {
        return heroRepository.findById(HERO_ID)
                .map(heroMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Hero content not found"));
    }
}
