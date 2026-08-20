package dhbart.portfolioapi.certification.infrastructure.persistence.repository;

import dhbart.portfolioapi.certification.infrastructure.persistence.entity.CertificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface CertificationJpaRepository extends JpaRepository<CertificationEntity, Long> {

    List<CertificationEntity> findAllByLocaleOrderByDisplayOrderAsc(String locale);
}
