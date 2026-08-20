package dhbart.portfolioapi.experience.domain.repository;

import dhbart.portfolioapi.experience.domain.model.Experience;
import java.util.List;

public interface ExperienceRepository {

    List<Experience> findAllByLocaleOrderByDisplayOrderDesc(String locale);
}
