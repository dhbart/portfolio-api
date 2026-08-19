package dhbart.portfolioapi.about.application.mapper;

import dhbart.portfolioapi.about.application.dto.AboutResponse;
import dhbart.portfolioapi.about.domain.model.About;
import org.springframework.stereotype.Component;

@Component
public class AboutMapper {

    public AboutResponse toResponse(About about) {
        return new AboutResponse(about.getId(), about.getTitle(), about.getDescription());
    }
}
