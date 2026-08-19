package dhbart.portfolioapi.about.infrastructure.persistence.repository;

import dhbart.portfolioapi.about.infrastructure.persistence.entity.AboutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface AboutJpaRepository extends JpaRepository<AboutEntity, Long> {
}
