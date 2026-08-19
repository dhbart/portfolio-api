package dhbart.portfolioapi.technology.application.controller;

import dhbart.portfolioapi.technology.application.dto.TechnologyResponse;
import dhbart.portfolioapi.technology.application.service.TechnologyService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping
    public ResponseEntity<List<TechnologyResponse>> getTechnologies() {
        return ResponseEntity.ok(technologyService.findAllTechnologies());
    }
}
