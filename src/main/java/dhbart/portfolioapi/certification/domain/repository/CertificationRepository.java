package dhbart.portfolioapi.certification.domain.repository;

import dhbart.portfolioapi.certification.domain.model.Certification;
import java.util.List;
import java.util.Optional;

public interface CertificationRepository {

    List<Certification> findAllByLocaleOrderByDisplayOrderAsc(String locale);

    Optional<Certification> findByIdAndLocale(Long id, String locale);
}
