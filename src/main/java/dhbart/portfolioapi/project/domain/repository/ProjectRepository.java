package dhbart.portfolioapi.project.domain.repository;

import dhbart.portfolioapi.project.domain.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<Project> findAllByOrderByDisplayOrderAsc();

    Optional<Project> findBySlug(String slug);
}
