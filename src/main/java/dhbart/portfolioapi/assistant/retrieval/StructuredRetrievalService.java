package dhbart.portfolioapi.assistant.retrieval;

import dhbart.portfolioapi.about.application.service.AboutService;
import dhbart.portfolioapi.certification.application.service.CertificationService;
import dhbart.portfolioapi.experience.application.service.ExperienceService;
import dhbart.portfolioapi.hero.application.service.HeroService;
import dhbart.portfolioapi.project.application.service.ProjectService;
import dhbart.portfolioapi.sociallink.application.service.SocialLinkService;
import dhbart.portfolioapi.technology.application.service.TechnologyService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StructuredRetrievalService {
    private final HeroService heroService;
    private final AboutService aboutService;
    private final ExperienceService experienceService;
    private final ProjectService projectService;
    private final CertificationService certificationService;
    private final TechnologyService technologyService;
    private final SocialLinkService socialLinkService;

    public StructuredRetrievalService(HeroService heroService, AboutService aboutService,
            ExperienceService experienceService, ProjectService projectService,
            CertificationService certificationService, TechnologyService technologyService,
            SocialLinkService socialLinkService) {
        this.heroService = heroService;
        this.aboutService = aboutService;
        this.experienceService = experienceService;
        this.projectService = projectService;
        this.certificationService = certificationService;
        this.technologyService = technologyService;
        this.socialLinkService = socialLinkService;
    }

    public Map<String, String> retrieve(String question) {
        String q = question.toLowerCase(Locale.ROOT);
        boolean all = containsAny(q, "who is daniel", "tell me about daniel", "perfil geral", "profile");
        Map<String, String> result = new LinkedHashMap<>();
        if (all || containsAny(q, "who is", "about daniel", "profile", "perfil")) add(result, "PROFILE", () ->
                format("Hero", heroService.findHero(null), "About", aboutService.findAbout(null)));
        if (all || containsAny(q, "experience", "worked", "work", "company", "fruki", "erp", "leadership", "liderança"))
            add(result, "EXPERIENCE", () -> format("Experience", experienceService.findAllExperiences(null)));
        if (all || containsAny(q, "project", "projeto", "migration", "migração"))
            add(result, "PROJECTS", () -> format("Projects", projectService.findAllProjects(null)));
        if (all || containsAny(q, "certification", "certifications", "certificate", "certificações"))
            add(result, "CERTIFICATIONS", () -> format("Certifications", certificationService.findAllCertifications(null)));
        if (all || containsAny(q, "technology", "technologies", "skill", "java", "tecnologia", "tecnologias"))
            add(result, "TECHNOLOGIES", () -> format("Technologies", technologyService.findAllTechnologies()));
        if (all || containsAny(q, "social", "linkedin", "github", "contact"))
            add(result, "SOCIAL LINKS", () -> format("Social links", socialLinkService.findAllSocialLinks()));
        return result;
    }

    private void add(Map<String, String> result, String section, java.util.function.Supplier<String> source) {
        result.put(section, source.get());
    }

    private boolean containsAny(String value, String... terms) {
        for (String term : terms) if (value.contains(term)) return true;
        return false;
    }

    private String format(String label, Object value) {
        return label + ":\n" + value;
    }

    private String format(String firstLabel, Object first, String secondLabel, Object second) {
        return firstLabel + ":\n" + first + "\n\n" + secondLabel + ":\n" + second;
    }
}
