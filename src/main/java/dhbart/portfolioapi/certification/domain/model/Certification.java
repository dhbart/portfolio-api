package dhbart.portfolioapi.certification.domain.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certification {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private String issuer;
    private String description;

    @NotNull
    private CertificationType certificationType;

    private LocalDate issueDate;
    private String credentialCode;
    private String credentialUrl;
    private String imageUrl;

    @NotNull
    private Integer displayOrder;
}
