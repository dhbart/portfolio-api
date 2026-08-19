package dhbart.portfolioapi.sociallink.application.dto;

public record SocialLinkResponse(
        Long id,
        String label,
        String value,
        String url,
        String icon,
        Integer displayOrder
) {
}
