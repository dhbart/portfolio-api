package dhbart.portfolioapi.certification.infrastructure.persistence.repository;

import dhbart.portfolioapi.certification.domain.model.Certification;
import dhbart.portfolioapi.certification.domain.repository.CertificationRepository;
import dhbart.portfolioapi.certification.infrastructure.persistence.mapper.CertificationEntityMapper;
import java.util.List;
import java.util.Optional;

import dhbart.portfolioapi.project.domain.model.Project;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class CertificationRepositoryImpl implements CertificationRepository {

    private final CertificationJpaRepository repository;
    private final CertificationEntityMapper mapper;

    public CertificationRepositoryImpl(CertificationJpaRepository repository, CertificationEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Certification> findAllByLocaleOrderByDisplayOrderAsc(String locale) {
        return repository.findAllByLocaleOrderByDisplayOrderAsc(locale).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Certification> findByIdAndLocale(Long id, String locale) {
        return repository.findByIdAndLocale(id, locale).map(mapper::toDomain);
    }
}
