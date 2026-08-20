package dhbart.portfolioapi.certification.domain.repository;

import dhbart.portfolioapi.certification.domain.model.Certification;
import java.util.List;

public interface CertificationRepository {

    List<Certification> findAllByLocaleOrderByDisplayOrderAsc(String locale);
}
