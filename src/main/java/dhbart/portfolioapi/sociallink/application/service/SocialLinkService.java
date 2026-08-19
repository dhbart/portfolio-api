package dhbart.portfolioapi.sociallink.application.service;

import dhbart.portfolioapi.sociallink.application.dto.SocialLinkResponse;
import dhbart.portfolioapi.sociallink.application.mapper.SocialLinkMapper;
import dhbart.portfolioapi.sociallink.domain.repository.SocialLinkRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SocialLinkService {

    private final SocialLinkRepository socialLinkRepository;
    private final SocialLinkMapper socialLinkMapper;

    public SocialLinkService(SocialLinkRepository socialLinkRepository, SocialLinkMapper socialLinkMapper) {
        this.socialLinkRepository = socialLinkRepository;
        this.socialLinkMapper = socialLinkMapper;
    }

    public List<SocialLinkResponse> findAllSocialLinks() {
        return socialLinkRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(socialLinkMapper::toResponse)
                .toList();
    }
}
