package dhbart.portfolioapi.hero.domain.model;

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
public class Hero {

    @NotNull
    private Long id;

    private String locale;

    private String greeting;

    @NotBlank
    private String name;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String primaryButtonLabel;
    private String primaryButtonUrl;
    private String secondaryButtonLabel;
    private String secondaryButtonUrl;
}
