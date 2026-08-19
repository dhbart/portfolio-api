package dhbart.portfolioapi.about.application.service;

import dhbart.portfolioapi.about.application.dto.AboutResponse;
import dhbart.portfolioapi.about.application.mapper.AboutMapper;
import dhbart.portfolioapi.about.domain.repository.AboutRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class AboutService {

    private static final long ABOUT_ID = 1L;

    private final AboutRepository aboutRepository;
    private final AboutMapper aboutMapper;

    public AboutService(AboutRepository aboutRepository, AboutMapper aboutMapper) {
        this.aboutRepository = aboutRepository;
        this.aboutMapper = aboutMapper;
    }

    public AboutResponse findAbout() {
        return aboutRepository.findById(ABOUT_ID)
                .map(aboutMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "About content not found"));
    }
}
