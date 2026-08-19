package dhbart.portfolioapi.sociallink.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "social_link")
public class SocialLinkEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private String icon;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected SocialLinkEntity() {
    }

    public SocialLinkEntity(Long id, String label, String value, String url, String icon,
                            Integer displayOrder, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.url = url;
        this.icon = icon;
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
    public String getLabel() { return label; }
    public String getValue() { return value; }
    public String getUrl() { return url; }
    public String getIcon() { return icon; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
