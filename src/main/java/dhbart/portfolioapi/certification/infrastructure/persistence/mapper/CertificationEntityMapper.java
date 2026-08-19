package dhbart.portfolioapi.certification.infrastructure.persistence.mapper;

import dhbart.portfolioapi.certification.domain.model.Certification;
import dhbart.portfolioapi.certification.infrastructure.persistence.entity.CertificationEntity;
import org.springframework.stereotype.Component;

@Component
public class CertificationEntityMapper {

    public Certification toDomain(CertificationEntity entity) {
        return Certification.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .issuer(entity.getIssuer())
                .description(entity.getDescription())
                .certificationType(entity.getCertificationType())
                .issueDate(entity.getIssueDate())
                .credentialCode(entity.getCredentialCode())
                .credentialUrl(entity.getCredentialUrl())
                .imageUrl(entity.getImageUrl())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    public CertificationEntity toEntity(Certification certification) {
        return new CertificationEntity(
                certification.getId(), certification.getTitle(), certification.getIssuer(), certification.getDescription(),
                certification.getCertificationType(), certification.getIssueDate(), certification.getCredentialCode(),
                certification.getCredentialUrl(), certification.getImageUrl(), certification.getDisplayOrder(), null, null);
    }
}
