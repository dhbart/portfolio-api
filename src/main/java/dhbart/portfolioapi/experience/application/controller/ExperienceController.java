package dhbart.portfolioapi.experience.application.controller;

import dhbart.portfolioapi.experience.application.dto.ExperienceResponse;
import dhbart.portfolioapi.experience.application.service.ExperienceService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getExperiences(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(experienceService.findAllExperiences(acceptLanguage));
    }
}
