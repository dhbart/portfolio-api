package dhbart.portfolioapi.project.infrastructure.persistence.repository;

import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectTechnologyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectTechnologyJpaRepository extends JpaRepository<ProjectTechnologyEntity, Long> {

    List<ProjectTechnologyEntity> findAllByProject_IdOrderByDisplayOrderAsc(Long projectId);
}
