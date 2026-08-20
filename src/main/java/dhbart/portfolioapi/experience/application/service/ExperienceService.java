package dhbart.portfolioapi.experience.application.service;

import dhbart.portfolioapi.experience.application.dto.ExperienceResponse;
import dhbart.portfolioapi.experience.application.mapper.ExperienceMapper;
import dhbart.portfolioapi.experience.domain.repository.ExperienceRepository;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final LocaleResolver localeResolver;

    public ExperienceService(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper,
                             LocaleResolver localeResolver) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.localeResolver = localeResolver;
    }

    public List<ExperienceResponse> findAllExperiences(String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var experiences = experienceRepository.findAllByLocaleOrderByDisplayOrderDesc(locale);
            if (!experiences.isEmpty()) return experiences.stream()
                    .map(experienceMapper::toResponse)
                    .toList();
        }
        return List.of();
    }
}
