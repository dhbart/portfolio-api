package dhbart.portfolioapi.technology.infrastructure.persistence.repository;

import dhbart.portfolioapi.technology.infrastructure.persistence.entity.TechnologyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface TechnologyJpaRepository extends JpaRepository<TechnologyEntity, Long> {

    List<TechnologyEntity> findAllByOrderByDisplayOrderAsc();
}
