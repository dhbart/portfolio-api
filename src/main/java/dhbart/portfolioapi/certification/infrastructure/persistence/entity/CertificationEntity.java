package dhbart.portfolioapi.certification.infrastructure.persistence.entity;

import dhbart.portfolioapi.certification.domain.model.CertificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "certification")
public class CertificationEntity {

    @Id
    private Long id;

    @Column(nullable = false, length = 10)
    private String locale;

    @Column(nullable = false)
    private String title;

    private String issuer;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificationType certificationType;

    private LocalDate issueDate;
    private String credentialCode;
    private String credentialUrl;
    private String imageUrl;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected CertificationEntity() {
    }

    public CertificationEntity(Long id, String locale, String title, String issuer, String description,
                               CertificationType certificationType, LocalDate issueDate, String credentialCode,
                               String credentialUrl, String imageUrl, Integer displayOrder,
                               Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.locale = locale;
        this.title = title;
        this.issuer = issuer;
        this.description = description;
        this.certificationType = certificationType;
        this.issueDate = issueDate;
        this.credentialCode = credentialCode;
        this.credentialUrl = credentialUrl;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getLocale() { return locale; }
    public String getTitle() { return title; }
    public String getIssuer() { return issuer; }
    public String getDescription() { return description; }
    public CertificationType getCertificationType() { return certificationType; }
    public LocalDate getIssueDate() { return issueDate; }
    public String getCredentialCode() { return credentialCode; }
    public String getCredentialUrl() { return credentialUrl; }
    public String getImageUrl() { return imageUrl; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
