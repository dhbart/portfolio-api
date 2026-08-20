package dhbart.portfolioapi.project.infrastructure.persistence.repository;

import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByLocaleOrderByDisplayOrderAsc(String locale);

    Optional<ProjectEntity> findBySlugAndLocale(String slug, String locale);
}
