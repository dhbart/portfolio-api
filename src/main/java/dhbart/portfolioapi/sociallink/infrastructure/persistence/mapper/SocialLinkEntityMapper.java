package dhbart.portfolioapi.sociallink.infrastructure.persistence.mapper;

import dhbart.portfolioapi.sociallink.domain.model.SocialLink;
import dhbart.portfolioapi.sociallink.infrastructure.persistence.entity.SocialLinkEntity;
import org.springframework.stereotype.Component;

@Component
public class SocialLinkEntityMapper {

    public SocialLink toDomain(SocialLinkEntity entity) {
        return SocialLink.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .value(entity.getValue())
                .url(entity.getUrl())
                .icon(entity.getIcon())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    public SocialLinkEntity toEntity(SocialLink socialLink) {
        return new SocialLinkEntity(
                socialLink.getId(), socialLink.getLabel(), socialLink.getValue(),
                socialLink.getUrl(), socialLink.getIcon(), socialLink.getDisplayOrder(), null, null);
    }
}
