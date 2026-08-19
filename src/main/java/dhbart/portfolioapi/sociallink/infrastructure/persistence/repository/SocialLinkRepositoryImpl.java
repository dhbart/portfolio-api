package dhbart.portfolioapi.sociallink.infrastructure.persistence.repository;

import dhbart.portfolioapi.sociallink.domain.model.SocialLink;
import dhbart.portfolioapi.sociallink.domain.repository.SocialLinkRepository;
import dhbart.portfolioapi.sociallink.infrastructure.persistence.mapper.SocialLinkEntityMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class SocialLinkRepositoryImpl implements SocialLinkRepository {

    private final SocialLinkJpaRepository repository;
    private final SocialLinkEntityMapper mapper;

    public SocialLinkRepositoryImpl(SocialLinkJpaRepository repository, SocialLinkEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<SocialLink> findAllByOrderByDisplayOrderAsc() {
        return repository.findAllByOrderByDisplayOrderAsc().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
