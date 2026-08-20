package dhbart.portfolioapi.project.infrastructure.persistence.repository;

import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectTechnologyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ProjectTechnologyJpaRepository extends JpaRepository<ProjectTechnologyEntity, Long> {

    @Query("select relationship from ProjectTechnologyEntity relationship "
            + "join fetch relationship.project "
            + "join fetch relationship.technology "
            + "where relationship.project.id = :projectId "
            + "order by relationship.displayOrder asc")
    List<ProjectTechnologyEntity> findAllByProjectId(@Param("projectId") Long projectId);

    @Query("select relationship from ProjectTechnologyEntity relationship "
            + "join fetch relationship.project "
            + "join fetch relationship.technology "
            + "where relationship.project.id in :projectIds "
            + "order by relationship.project.id asc, relationship.displayOrder asc")
    List<ProjectTechnologyEntity> findAllByProjectIds(@Param("projectIds") List<Long> projectIds);
}
