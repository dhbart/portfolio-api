package dhbart.portfolioapi.sociallink.application.service;

import dhbart.portfolioapi.sociallink.application.dto.SocialLinkResponse;
import dhbart.portfolioapi.sociallink.application.mapper.SocialLinkMapper;
import dhbart.portfolioapi.sociallink.domain.model.SocialLink;
import dhbart.portfolioapi.sociallink.domain.repository.SocialLinkRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialLinkServiceTest {

    @Mock
    private SocialLinkRepository socialLinkRepository;

    @Mock
    private SocialLinkMapper socialLinkMapper;

    @Test
    void shouldReturnSocialLinksInRepositoryOrder() {
        SocialLink first = SocialLink.builder().id(1L).label("LinkedIn").build();
        SocialLink second = SocialLink.builder().id(2L).label("GitHub").build();
        SocialLinkResponse firstResponse = new SocialLinkResponse(1L, "LinkedIn", "value", "url", "linkedin", 1);
        SocialLinkResponse secondResponse = new SocialLinkResponse(2L, "GitHub", "value", "url", "github", 2);
        when(socialLinkRepository.findAllByOrderByDisplayOrderAsc()).thenReturn(List.of(first, second));
        when(socialLinkMapper.toResponse(first)).thenReturn(firstResponse);
        when(socialLinkMapper.toResponse(second)).thenReturn(secondResponse);

        List<SocialLinkResponse> result = new SocialLinkService(socialLinkRepository, socialLinkMapper)
                .findAllSocialLinks();

        assertThat(result).containsExactly(firstResponse, secondResponse);
    }
}
