package dhbart.portfolioapi.technology.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "technology")
public class TechnologyEntity {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 100)
    private String icon;

    @Column(length = 2048)
    private String website;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected TechnologyEntity() {
    }

    public TechnologyEntity(Long id, String name, String slug, String icon, String website,
                            Integer displayOrder, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.icon = icon;
        this.website = website;
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
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getIcon() { return icon; }
    public String getWebsite() { return website; }
    public Integer getDisplayOrder() { return displayOrder; }
}
