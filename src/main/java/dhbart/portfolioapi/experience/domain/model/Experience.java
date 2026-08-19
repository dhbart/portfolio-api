package dhbart.portfolioapi.experience.domain.model;

import java.time.LocalDate;
import java.util.List;
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
public class Experience {

    @NotNull
    private Long id;

    @NotBlank
    private String company;

    private String location;

    private String period;

    @NotBlank
    private String position;

    @NotBlank
    private String summary;

    private List<String> description;

    private List<String> highlights;

    private List<String> technologies;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Boolean currentPosition;

    @NotNull
    private Integer displayOrder;
}
