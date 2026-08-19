package dhbart.portfolioapi.technology.application.service;

import dhbart.portfolioapi.technology.application.dto.TechnologyResponse;
import dhbart.portfolioapi.technology.application.mapper.TechnologyMapper;
import dhbart.portfolioapi.technology.domain.repository.TechnologyRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    public TechnologyService(TechnologyRepository technologyRepository, TechnologyMapper technologyMapper) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
    }

    public List<TechnologyResponse> findAllTechnologies() {
        return technologyRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(technologyMapper::toResponse)
                .toList();
    }
}
