package dhbart.portfolioapi.project.domain.model;

import dhbart.portfolioapi.technology.domain.model.Technology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTechnology {

    private Project project;
    private Technology technology;
    private Integer displayOrder;
}
