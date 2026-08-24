package dhbart.portfolioapi.about.application.service;

import dhbart.portfolioapi.about.application.dto.AboutResponse;
import dhbart.portfolioapi.about.application.mapper.AboutMapper;
import dhbart.portfolioapi.about.domain.repository.AboutRepository;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import dhbart.portfolioapi.config.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AboutService {

    private final AboutRepository aboutRepository;
    private final AboutMapper aboutMapper;
    private final LocaleResolver localeResolver;

    public AboutService(
            AboutRepository aboutRepository,
            AboutMapper aboutMapper,
            LocaleResolver localeResolver
    ) {
        this.aboutRepository = aboutRepository;
        this.aboutMapper = aboutMapper;
        this.localeResolver = localeResolver;
    }

    @Cacheable(cacheNames = CacheNames.ABOUT, key = "#acceptLanguage ?: ''")
    public AboutResponse findAbout(String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var about = aboutRepository.findByLocale(locale);

            if (about.isPresent()) {
                return aboutMapper.toResponse(about.get());
            }
        }

        throw new ResourceNotFoundException("About content not found");
    }
}
