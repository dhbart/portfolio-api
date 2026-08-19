package dhbart.portfolioapi.experience.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "experience")
public class ExperienceEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summary;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> highlights;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private List<String> technologies;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean currentPosition;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected ExperienceEntity() {
    }

    public ExperienceEntity(Long id, String company, String location, String period, String position,
                            String summary, List<String> description, List<String> highlights,
                            List<String> technologies,
                            LocalDate startDate, LocalDate endDate, Boolean currentPosition,
                            Integer displayOrder, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.company = company;
        this.location = location;
        this.period = period;
        this.position = position;
        this.summary = summary;
        this.description = description;
        this.highlights = highlights;
        this.technologies = technologies;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentPosition = currentPosition;
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
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getPeriod() { return period; }
    public String getPosition() { return position; }
    public String getSummary() { return summary; }
    public List<String> getDescription() { return description; }
    public List<String> getHighlights() { return highlights; }
    public List<String> getTechnologies() { return technologies; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Boolean getCurrentPosition() { return currentPosition; }
    public Integer getDisplayOrder() { return displayOrder; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
