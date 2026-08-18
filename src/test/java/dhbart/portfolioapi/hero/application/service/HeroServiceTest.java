package dhbart.portfolioapi.hero.application.service;

import dhbart.portfolioapi.hero.application.dto.HeroResponse;
import dhbart.portfolioapi.hero.application.mapper.HeroMapper;
import dhbart.portfolioapi.hero.domain.model.Hero;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private HeroMapper heroMapper;

    @Test
    void shouldReturnMappedHero() {
        Hero hero = Hero.builder().id(1L).name("Daniel Henrique Bartholdy").build();
        HeroResponse response = new HeroResponse(
                1L, "Hi, I'm", "Daniel Henrique Bartholdy", "title", "description",
                "View Projects", "#projects", "Download Resume", "/resume.pdf");
        when(heroRepository.findById(1L)).thenReturn(Optional.of(hero));
        when(heroMapper.toResponse(hero)).thenReturn(response);

        HeroResponse result = new HeroService(heroRepository, heroMapper).findHero();

        assertThat(result).isSameAs(response);
    }

    @Test
    void shouldThrowNotFoundWhenHeroDoesNotExist() {
        when(heroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> new HeroService(heroRepository, heroMapper).findHero())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Hero content not found");
    }
}
