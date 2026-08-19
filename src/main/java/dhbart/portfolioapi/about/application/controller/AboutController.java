package dhbart.portfolioapi.about.application.controller;

import dhbart.portfolioapi.about.application.dto.AboutResponse;
import dhbart.portfolioapi.about.application.service.AboutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/about")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public ResponseEntity<AboutResponse> getAbout() {
        return ResponseEntity.ok(aboutService.findAbout());
    }
}
