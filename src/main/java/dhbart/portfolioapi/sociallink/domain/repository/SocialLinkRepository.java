package dhbart.portfolioapi.sociallink.domain.repository;

import dhbart.portfolioapi.sociallink.domain.model.SocialLink;
import java.util.List;

public interface SocialLinkRepository {

    List<SocialLink> findAllByOrderByDisplayOrderAsc();
}
