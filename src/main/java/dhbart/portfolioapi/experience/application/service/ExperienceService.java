package dhbart.portfolioapi.experience.application.service;

import dhbart.portfolioapi.experience.application.dto.ExperienceResponse;
import dhbart.portfolioapi.experience.application.mapper.ExperienceMapper;
import dhbart.portfolioapi.experience.domain.repository.ExperienceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public ExperienceService(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    public List<ExperienceResponse> findAllExperiences() {
        return experienceRepository.findAllByOrderByDisplayOrderDesc()
                .stream()
                .map(experienceMapper::toResponse)
                .toList();
    }
}
