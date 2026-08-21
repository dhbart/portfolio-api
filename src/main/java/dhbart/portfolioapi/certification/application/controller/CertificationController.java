package dhbart.portfolioapi.certification.application.controller;

import dhbart.portfolioapi.certification.application.dto.CertificationResponse;
import dhbart.portfolioapi.certification.application.service.CertificationService;
import java.util.List;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping
    public ResponseEntity<List<CertificationResponse>> getCertifications(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(certificationService.findAllCertifications(acceptLanguage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponse> get(@PathVariable Long id,
                                                      @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(certificationService.findCertification(id, acceptLanguage));
    }
}
