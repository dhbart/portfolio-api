package dhbart.portfolioapi.sociallink.application.controller;

import dhbart.portfolioapi.sociallink.application.dto.SocialLinkResponse;
import dhbart.portfolioapi.sociallink.application.service.SocialLinkService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/social-links")
public class SocialLinkController {

    private final SocialLinkService socialLinkService;

    public SocialLinkController(SocialLinkService socialLinkService) {
        this.socialLinkService = socialLinkService;
    }

    @GetMapping
    public ResponseEntity<List<SocialLinkResponse>> getSocialLinks() {
        return ResponseEntity.ok(socialLinkService.findAllSocialLinks());
    }
}
