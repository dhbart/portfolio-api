package dhbart.portfolioapi.certification.infrastructure.persistence.repository;

import dhbart.portfolioapi.certification.infrastructure.persistence.entity.CertificationEntity;
import java.util.List;
import java.util.Optional;

import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface CertificationJpaRepository extends JpaRepository<CertificationEntity, Long> {

    List<CertificationEntity> findAllByLocaleOrderByDisplayOrderAsc(String locale);

    Optional<CertificationEntity> findByIdAndLocale(Long id, String locale);
}
