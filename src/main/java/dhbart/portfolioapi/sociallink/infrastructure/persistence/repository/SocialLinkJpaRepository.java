package dhbart.portfolioapi.sociallink.infrastructure.persistence.repository;

import dhbart.portfolioapi.sociallink.infrastructure.persistence.entity.SocialLinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface SocialLinkJpaRepository extends JpaRepository<SocialLinkEntity, Long> {

    List<SocialLinkEntity> findAllByOrderByDisplayOrderAsc();
}
