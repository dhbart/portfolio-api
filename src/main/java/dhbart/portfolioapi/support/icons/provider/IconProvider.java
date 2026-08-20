package dhbart.portfolioapi.support.icons.provider;

import dhbart.portfolioapi.technology.domain.model.Technology;
import java.net.URI;
import java.util.Optional;

public interface IconProvider {
    Optional<URI> resolve(Technology technology);
}
