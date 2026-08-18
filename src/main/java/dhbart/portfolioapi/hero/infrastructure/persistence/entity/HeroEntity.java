package dhbart.portfolioapi.hero.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "hero")
public class HeroEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String greeting;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String primaryButtonLabel;

    @Column(nullable = false)
    private String primaryButtonUrl;

    @Column(nullable = false)
    private String secondaryButtonLabel;

    @Column(nullable = false)
    private String secondaryButtonUrl;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected HeroEntity() {
    }

    public HeroEntity(Long id, String greeting, String name, String title, String description,
                      String primaryButtonLabel, String primaryButtonUrl,
                      String secondaryButtonLabel, String secondaryButtonUrl,
                      Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.greeting = greeting;
        this.name = name;
        this.title = title;
        this.description = description;
        this.primaryButtonLabel = primaryButtonLabel;
        this.primaryButtonUrl = primaryButtonUrl;
        this.secondaryButtonLabel = secondaryButtonLabel;
        this.secondaryButtonUrl = secondaryButtonUrl;
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
    public String getGreeting() { return greeting; }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPrimaryButtonLabel() { return primaryButtonLabel; }
    public String getPrimaryButtonUrl() { return primaryButtonUrl; }
    public String getSecondaryButtonLabel() { return secondaryButtonLabel; }
    public String getSecondaryButtonUrl() { return secondaryButtonUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
