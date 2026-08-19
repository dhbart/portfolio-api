package dhbart.portfolioapi.project.domain.repository;

import dhbart.portfolioapi.project.domain.model.ProjectTechnology;
import java.util.List;

public interface ProjectTechnologyRepository {

    List<ProjectTechnology> findAllByProjectIdOrderByDisplayOrderAsc(Long projectId);
}
