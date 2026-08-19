package dhbart.portfolioapi.certification.application.dto;

import dhbart.portfolioapi.certification.domain.model.CertificationType;
import java.time.LocalDate;

public record CertificationRequest(
        String title,
        String issuer,
        String description,
        CertificationType certificationType,
        LocalDate issueDate,
        String credentialCode,
        String credentialUrl,
        String imageUrl,
        Integer displayOrder
) {
}
