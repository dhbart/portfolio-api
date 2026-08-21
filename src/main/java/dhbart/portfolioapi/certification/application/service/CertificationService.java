package dhbart.portfolioapi.certification.application.service;

import dhbart.portfolioapi.certification.application.dto.CertificationResponse;
import dhbart.portfolioapi.certification.application.mapper.CertificationMapper;
import dhbart.portfolioapi.certification.domain.repository.CertificationRepository;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import java.util.List;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;
    private final LocaleResolver localeResolver;

    public CertificationService(CertificationRepository certificationRepository, CertificationMapper certificationMapper,
                                LocaleResolver localeResolver) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
        this.localeResolver = localeResolver;
    }

    public List<CertificationResponse> findAllCertifications(String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var certifications = certificationRepository.findAllByLocaleOrderByDisplayOrderAsc(locale);
            if (!certifications.isEmpty()) return certifications.stream()
                    .map(certificationMapper::toResponse)
                    .toList();
        }
        return List.of();
    }

    public CertificationResponse findCertification(Long id, String acceptLanguage){
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var certification = certificationRepository.findByIdAndLocale(id, locale);
            if (certification.isPresent()) return certificationMapper.toResponse(certification.get());
        }
        throw new ResourceNotFoundException("Certification not found");
    }


}
