package dhbart.portfolioapi.project.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Project {

    @NotNull
    private Long id;

    @NotBlank
    private String slug;

    @NotBlank
    private String title;

    @NotBlank
    private String headline;

    @NotBlank
    private String description;

    @NotBlank
    private String challenge;

    @NotBlank
    private String solution;

    private String imageUrl;
    private String githubUrl;
    private String demoUrl;

    @NotNull
    private Boolean featured;

    @NotNull
    private Integer displayOrder;
}
