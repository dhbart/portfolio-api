package dhbart.portfolioapi.hero.application.controller;

import dhbart.portfolioapi.hero.application.dto.HeroResponse;
import dhbart.portfolioapi.hero.application.service.HeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hero")
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping
    public ResponseEntity<HeroResponse> getHero(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(heroService.findHero(acceptLanguage));
    }
}
