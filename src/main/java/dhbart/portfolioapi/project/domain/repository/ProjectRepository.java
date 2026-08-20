package dhbart.portfolioapi.project.domain.repository;

import dhbart.portfolioapi.project.domain.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAllByLocaleOrderByDisplayOrderAsc(String locale);

    Optional<Project> findBySlugAndLocale(String slug, String locale);
}
