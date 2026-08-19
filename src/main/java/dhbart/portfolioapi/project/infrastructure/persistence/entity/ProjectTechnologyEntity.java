package dhbart.portfolioapi.project.infrastructure.persistence.entity;

import dhbart.portfolioapi.technology.infrastructure.persistence.entity.TechnologyEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "project_technology")
public class ProjectTechnologyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "technology_id", nullable = false)
    private TechnologyEntity technology;

    @Column(nullable = false)
    private Integer displayOrder;

    protected ProjectTechnologyEntity() {
    }

    public ProjectTechnologyEntity(ProjectEntity project, TechnologyEntity technology, Integer displayOrder) {
        this.project = project;
        this.technology = technology;
        this.displayOrder = displayOrder;
    }

    public ProjectEntity getProject() { return project; }
    public TechnologyEntity getTechnology() { return technology; }
    public Integer getDisplayOrder() { return displayOrder; }
}
