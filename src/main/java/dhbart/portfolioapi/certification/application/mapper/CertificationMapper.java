package dhbart.portfolioapi.certification.application.mapper;

import dhbart.portfolioapi.certification.application.dto.CertificationRequest;
import dhbart.portfolioapi.certification.application.dto.CertificationResponse;
import dhbart.portfolioapi.certification.domain.model.Certification;
import org.springframework.stereotype.Component;

@Component
public class CertificationMapper {

    public CertificationResponse toResponse(Certification certification) {
        return new CertificationResponse(
                certification.getId(), certification.getTitle(), certification.getIssuer(),
                certification.getDescription(), certification.getCertificationType(), certification.getIssueDate(),
                certification.getCredentialCode(), certification.getCredentialUrl(), certification.getImageUrl(),
                certification.getDisplayOrder());
    }

    public Certification toDomain(CertificationRequest request) {
        return Certification.builder()
                .title(request.title())
                .issuer(request.issuer())
                .description(request.description())
                .certificationType(request.certificationType())
                .issueDate(request.issueDate())
                .credentialCode(request.credentialCode())
                .credentialUrl(request.credentialUrl())
                .imageUrl(request.imageUrl())
                .displayOrder(request.displayOrder())
                .build();
    }
}
