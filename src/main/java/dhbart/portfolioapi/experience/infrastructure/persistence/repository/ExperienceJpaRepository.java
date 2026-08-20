package dhbart.portfolioapi.experience.infrastructure.persistence.repository;

import dhbart.portfolioapi.experience.infrastructure.persistence.entity.ExperienceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface ExperienceJpaRepository extends JpaRepository<ExperienceEntity, Long> {

    List<ExperienceEntity> findAllByLocaleOrderByDisplayOrderDesc(String locale);
}
