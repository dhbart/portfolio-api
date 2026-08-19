package dhbart.portfolioapi.project.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String headline;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String challenge;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String solution;

    @Column(length = 2048)
    private String imageUrl;

    @Column(length = 2048)
    private String githubUrl;

    @Column(length = 2048)
    private String demoUrl;

    @Column(nullable = false)
    private Boolean featured;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected ProjectEntity() {
    }

    public ProjectEntity(Long id, String slug, String title, String headline, String description,
                         String challenge, String solution, String imageUrl, String githubUrl,
                         String demoUrl, Boolean featured, Integer displayOrder,
                         Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.headline = headline;
        this.description = description;
        this.challenge = challenge;
        this.solution = solution;
        this.imageUrl = imageUrl;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.featured = featured;
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
    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public String getHeadline() { return headline; }
    public String getDescription() { return description; }
    public String getChallenge() { return challenge; }
    public String getSolution() { return solution; }
    public String getImageUrl() { return imageUrl; }
    public String getGithubUrl() { return githubUrl; }
    public String getDemoUrl() { return demoUrl; }
    public Boolean getFeatured() { return featured; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
