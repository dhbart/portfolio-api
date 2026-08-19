package dhbart.portfolioapi.technology.domain.repository;

import dhbart.portfolioapi.technology.domain.model.Technology;
import java.util.List;

public interface TechnologyRepository {

    List<Technology> findAllByOrderByDisplayOrderAsc();
}
