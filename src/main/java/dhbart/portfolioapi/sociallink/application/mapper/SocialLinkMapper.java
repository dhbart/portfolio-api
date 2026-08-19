package dhbart.portfolioapi.sociallink.application.mapper;

import dhbart.portfolioapi.sociallink.application.dto.SocialLinkResponse;
import dhbart.portfolioapi.sociallink.domain.model.SocialLink;
import org.springframework.stereotype.Component;

@Component
public class SocialLinkMapper {

    public SocialLinkResponse toResponse(SocialLink socialLink) {
        return new SocialLinkResponse(
                socialLink.getId(), socialLink.getLabel(), socialLink.getValue(),
                socialLink.getUrl(), socialLink.getIcon(), socialLink.getDisplayOrder());
    }
}
