package dhbart.portfolioapi.certification.application.service;

import dhbart.portfolioapi.certification.application.dto.CertificationResponse;
import dhbart.portfolioapi.certification.application.mapper.CertificationMapper;
import dhbart.portfolioapi.certification.domain.repository.CertificationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    public CertificationService(CertificationRepository certificationRepository, CertificationMapper certificationMapper) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
    }

    public List<CertificationResponse> findAllCertifications() {
        return certificationRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(certificationMapper::toResponse)
                .toList();
    }
}
