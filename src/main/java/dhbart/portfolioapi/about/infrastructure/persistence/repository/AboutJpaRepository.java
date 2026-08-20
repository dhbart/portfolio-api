package dhbart.portfolioapi.about.infrastructure.persistence.repository;

import dhbart.portfolioapi.about.infrastructure.persistence.entity.AboutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface AboutJpaRepository extends JpaRepository<AboutEntity, Long> {
    Optional<AboutEntity> findByLocale(String locale);
    Optional<AboutEntity> findByIdAndLocale(Long id, String locale);
}
