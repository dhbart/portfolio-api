package dhbart.portfolioapi.about.domain.repository;

import dhbart.portfolioapi.about.domain.model.About;
import java.util.Optional;

public interface AboutRepository {

    Optional<About> findById(Long id);
}
